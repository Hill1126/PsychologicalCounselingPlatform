package org.gdou.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.common.utils.RedisUtil;
import org.gdou.dao.ArticleMapper;
import org.gdou.dao.ConfigMapper;
import org.gdou.model.bo.SearchArticleBo;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.po.Article;
import org.gdou.model.vo.article.ArticlePreviewVo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/1
 **/
@Service
@Slf4j
public class ArticleService {

    private ArticleMapper articleMapper;
    private ConfigMapper configMapper;
    private RedisUtil redisUtil;
    private RestHighLevelClient restHighLevelClient;
    private ObjectMapper objectMapper;

    public ArticleService(ArticleMapper articleMapper, ConfigMapper configMapper, RedisUtil redisUtil, RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) {
        this.articleMapper = articleMapper;
        this.configMapper = configMapper;
        this.redisUtil = redisUtil;
        this.restHighLevelClient = restHighLevelClient;
        this.objectMapper = objectMapper;
    }

    final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(5L));

    public Result getArticleCategory(){
        List<String> categoryList = configMapper.getValueByName("article", "category");
        return ResultGenerator.genSuccessResult(categoryList);
    }

    public Result getArticlePreview(PageInfoDto pageInfoDto, String category) {
        PageHelper.startPage(pageInfoDto.getPageNum(),pageInfoDto.getPageSize());
        List<ArticlePreviewVo> articlePreview = articleMapper.getArticlePreview(category);
        return Result.genSuccessResult(PageInfo.of(articlePreview));
    }

    public Result getArticleById(Integer articleId) throws IOException {
        String articleJson = redisUtil.get(ProjectConstant.ARTICLE_KEY + articleId);
        Article article = null;
        if (articleJson==null){
            //redis中不存在则从数据库获取拼放入redis中。
            article= articleMapper.selectByPrimaryKey(articleId);
            if (article!=null){
                article.setArticleContent(null);
                redisUtil.setEx(ProjectConstant.ARTICLE_KEY+articleId,
                        objectMapper.writeValueAsString(article), ProjectConstant.ORDER_KEY_EXPIRE);
            }else{
                return Result.genNotFound("您要查看的内容不存在");
            }
        }else {
            //更新redis的时间
            article = objectMapper.readValue(articleJson,Article.class);
            redisUtil.expire(ProjectConstant.ARTICLE_KEY+articleId,ProjectConstant.ORDER_KEY_EXPIRE);
        }

        return Result.genSuccessResult(article);
    }

    /**
     * 1.确定搜索的范围：文章、作者、来源、种类
     * 2.确定返回的数据：ArticlePreviewVo
     * 3.确定排序顺序：相关度排序（待定）
     * 4.确定分页信息：
     * @Author: HILL
     * @date: 2020/4/9 17:45
     *
     * @param keyWord 搜索的关键词
     * @param scrollId 搜索记录的滚动id
     * @return:  携带新的滚动id和查询结果。
    **/
    public SearchArticleBo searchArticle(String keyWord, String scrollId) throws IOException {

        SearchResponse response ;
        if (!StringUtils.isEmpty(scrollId)){
            log.info("文章搜索通过scrollId翻页，id为【{}】",scrollId);
            //如果scrollId不为空，直接通过翻页返回信息
            var scrollRequest = new SearchScrollRequest(scrollId);
            //默认5分钟
            scrollRequest.scroll(TimeValue.timeValueSeconds(ProjectConstant.SCROLL_ID_EXPIRE_TIME));
            response = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
        }else{
            log.info("文章通过关键词搜索，关键词为【{}】",keyWord);
            var searchRequest = new SearchRequest(ProjectConstant.ARTICLE_INDEX_NAME);
            searchRequest.scroll(scroll);
            var searchSourceBuilder = new SearchSourceBuilder();
            //确定搜索的范围：articleCategory、articleContent、articleSource、author、title
            var matchQuery = QueryBuilders.multiMatchQuery(keyWord,"articleCategory",
                    "articleContent","articleSource","author","title");
            searchSourceBuilder.query(matchQuery);
            //筛选状态,限定返回的条数
            searchSourceBuilder.size(5);
            //进行查询
            searchRequest.source(searchSourceBuilder);
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        }
        //根据response获取查询结果，并包装成Bo
        var list = new ArrayList<ArticlePreviewVo>(5);
        if (response!=null){
            Iterator<SearchHit> iterator = response.getHits().iterator();
            while (iterator.hasNext()){
                SearchHit searchHit = iterator.next();
                var sourceJson = searchHit.getSourceAsString();
                var previewVo = objectMapper.readValue(sourceJson, ArticlePreviewVo.class);
                list.add(previewVo);
            }
            log.info("搜索完成，搜索当前返回结果数为【{}】，总数目为【{}】",list.size(),response.getHits().totalHits);
            return new SearchArticleBo(response.getScrollId(),list);
        }

        return  null;

    }
}

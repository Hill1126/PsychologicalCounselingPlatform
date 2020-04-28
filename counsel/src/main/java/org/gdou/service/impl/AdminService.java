package org.gdou.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.constant.article.ArticleStatus;
import org.gdou.common.exception.runtime.EsIndexException;
import org.gdou.common.result.Result;
import org.gdou.common.utils.RedisUtil;
import org.gdou.dao.ArticleMapper;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.po.Article;
import org.gdou.model.vo.article.ArticlePreviewVo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/8
 **/
@Service
@Slf4j
public class AdminService {

    RestHighLevelClient restHighLevelClient;

    ArticleMapper articleMapper;

    ObjectMapper objectMapper;

    RedisUtil redisUtil;

    public AdminService(RestHighLevelClient restHighLevelClient, ArticleMapper articleMapper, ObjectMapper objectMapper, RedisUtil redisUtil) {
        this.restHighLevelClient = restHighLevelClient;
        this.articleMapper = articleMapper;
        this.objectMapper = objectMapper;
        this.redisUtil = redisUtil;
    }

    public Result putArticle(Article article) throws JsonProcessingException {
        //将获取到的文章插入到mysql数据库
        articleMapper.insert(article);
        log.info("mysql数据库插入文章，插入id为【{}】",article.getId());
        //将article插入es
        IndexRequest indexRequest = new IndexRequest(ProjectConstant.ARTICLE_INDEX_NAME,
                ProjectConstant.ARTICLE_TYPE_NAME,article.getId().toString());
        indexRequest.source(objectMapper.writeValueAsString(article), XContentType.JSON);
        IndexResponse response = null;
        try {
            response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new EsIndexException(e.getMessage(),article);
        }
        log.info("es数据库插入文章，文章的id为【{}】",response.getId());
        return Result.genSuccessResult();
    }


    public Result listArticlePreviews(PageInfoDto pageInfoDto, String category) {
        PageHelper.startPage(pageInfoDto.getPageNum(),pageInfoDto.getPageSize());
        List<ArticlePreviewVo> articlePreview = articleMapper.getArticlePreview(category,ArticleStatus.NORMAL);
        return Result.genSuccessResult(PageInfo.of(articlePreview));

    }

    public Result getArticle(Integer articleId) {
       return Result.genSuccessResult(articleMapper.selectByPrimaryKey(articleId));
    }


    /**
     * 将url添加到redis的list队列中，
     * @Author: HILL
     * @date: 2020/4/28 16:10
     *
     * @param crawlUrl
     * @param category
     * @return: org.gdou.common.result.Result
    **/
    public Result addCrawlUrl(String crawlUrl, String category) {
        if (category==null){
            category = ProjectConstant.DEFAULT_CATEGORY;
        }
        redisUtil.lLeftPush(ProjectConstant.CRAWL_URL_KEY,crawlUrl+"@"+category);
        return Result.genSuccessResult();
    }
}

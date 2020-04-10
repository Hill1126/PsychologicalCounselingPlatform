package org.gdou.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.exception.runtime.EsIndexException;
import org.gdou.common.result.Result;
import org.gdou.dao.ArticleMapper;
import org.gdou.model.po.Article;
import org.springframework.stereotype.Service;

import java.io.IOException;

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

    public AdminService(RestHighLevelClient restHighLevelClient, ArticleMapper articleMapper, ObjectMapper objectMapper) {
        this.restHighLevelClient = restHighLevelClient;
        this.articleMapper = articleMapper;
        this.objectMapper = objectMapper;
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




}

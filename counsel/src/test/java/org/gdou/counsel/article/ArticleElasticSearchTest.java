package org.gdou.counsel.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.gdou.dao.ArticleMapper;
import org.gdou.model.po.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/4
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ArticleElasticSearchTest {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void indexTest() throws IOException {
        Integer integer = 1001011;
        Map map = new HashMap();
        Article article = articleMapper.selectByPrimaryKey(integer);
        IndexRequest request = new IndexRequest("test", "doc",integer.toString())
                .source(objectMapper.writeValueAsString(article), XContentType.JSON)
                .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);

        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);

    }

    @Test
    public void getTest() throws IOException {
        GetRequest getRequest = new GetRequest("test","doc",1001011+"");
        GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        Article article = objectMapper.readValue(documentFields.getSourceAsString(), Article.class);
    }

    @Test
    public void updateTest() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("test","doc",1001011+"");
        var map = new HashMap<String,Object>();
        map.put("articleSource","tencent");
        updateRequest.doc(map);
        restHighLevelClient.update(updateRequest,RequestOptions.DEFAULT);
    }

    @Test
    public void searchTest() throws IOException {
        /** 1.确定搜索的范围：文章、作者、来源、种类
         *  2.确定返回的数据：ArticlePreviewVo
         *  3.确定排序顺序：相关度排序（待定）
         *  4.确定分页信息：
         */
        SearchRequest searchRequest = new SearchRequest("article");
        var searchBuilder = new SearchSourceBuilder();
        searchBuilder.query(QueryBuilders.matchQuery("title","美方"));
        searchRequest.source(searchBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);


    }

    @Test
    public void scrollTest(){
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        SearchRequest searchRequest = new SearchRequest("article");
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title","美方"));
        searchRequest.source(searchSourceBuilder);
        try {
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();

            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();

        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        boolean succeeded = clearScrollResponse.isSucceeded();
        }catch (Exception e){

        }

    }

}

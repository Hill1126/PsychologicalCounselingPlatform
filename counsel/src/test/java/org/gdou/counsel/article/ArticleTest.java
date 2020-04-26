package org.gdou.counsel.article;

import org.gdou.dao.ArticleMapper;
import org.gdou.model.bo.SearchArticleBo;
import org.gdou.model.vo.article.ArticlePreviewVo;
import org.gdou.service.impl.ArticleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/2
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ArticleTest {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleService articleService;

    @Test
    public void getArticlePreviewTest(){
        List<ArticlePreviewVo> articlePreview = articleMapper.getArticlePreview(null);
        Assert.assertNotEquals(0,articlePreview.size());
        List<ArticlePreviewVo> preview = articleMapper.getArticlePreview("科技");

    }

    @Test
    public void searchArticleTest() throws IOException {
        SearchArticleBo test = articleService.searchArticle("美国", null);
        Assert.assertNotNull(test);
        String scrollId = test.getScrollId();
        SearchArticleBo searchArticle = articleService.searchArticle("美国", scrollId);

    }

}

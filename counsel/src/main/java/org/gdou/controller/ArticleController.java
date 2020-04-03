package org.gdou.controller;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.result.Result;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.service.impl.ArticleService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 负责心理知识页面处理
 * @author HILL
 * @version V1.0
 * @date 2020/4/1
 **/
@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping("/getArticleCategory")
    public Result getArticleCategory(){
        return articleService.getArticleCategory();
    }

    @RequestMapping("/preview")
    public Result getArticlePreview(PageInfoDto pageInfoDto,String category){
        return articleService.getArticlePreview(pageInfoDto,category);
    }

    @RequestMapping("/{articleId}")
    public Result getArticle(@PathVariable Integer articleId){
        return articleService.getArticleById(articleId);
    }


}
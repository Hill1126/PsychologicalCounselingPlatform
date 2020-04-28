package org.gdou.controller;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.result.Result;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.service.impl.ArticleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * 负责心理知识页面处理
 * @author HILL
 * @version V1.0
 * @date 2020/4/1
 **/
@RestController
@RequestMapping("/article")
@Slf4j
@Validated
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
    public Result getArticle(@PathVariable Integer articleId) throws IOException {
        return articleService.getArticleById(articleId);
    }


    @RequestMapping("/search")
    public Result searchArticle(@RequestParam(defaultValue = "false") boolean nextPage,
                                @NotBlank(message = "参数keyWord不能为空") String keyWord,
                                HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String scrollId = null;
        //如果需要翻页，则尝试获取scrollId
        if (nextPage){
            scrollId = (String) session.getAttribute(ProjectConstant.SCROLL_ID_NAME);
        }
        var searchArticleBo = articleService.searchArticle(keyWord,scrollId);
        if (searchArticleBo!=null){
            //如果是新内容，设置scrollId到session
            session.setAttribute(ProjectConstant.SCROLL_ID_NAME,searchArticleBo.getScrollId());
            return Result.genSuccessResult(searchArticleBo.getVoList());
        }
        return Result.genFailResult("查询失败");
    }

}

package org.gdou.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.gdou.common.constant.article.ArticleStatus;
import org.gdou.common.result.Result;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.dto.article.ArticleDto;
import org.gdou.model.po.Article;
import org.gdou.service.impl.AdminService;
import org.gdou.service.impl.ArticleService;
import org.gdou.service.impl.BosService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/8
 **/
@RestController
@Validated
@RequestMapping("/admin/article")
public class AdminArticleController {


    private AdminService adminService;

    private ArticleService articleService;

    private BosService bosService;

    public AdminArticleController(AdminService adminService, ArticleService articleService, BosService bosService) {
        this.adminService = adminService;
        this.articleService = articleService;
        this.bosService = bosService;
    }

    /**
     * 将爬虫获取的内容上传至数据库和同步更新到es
     * @Author: HILL
     * @date: 2020/4/8 14:24
     *
     * @param articleDto
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/put")
    public Result putArticle(ArticleDto articleDto) throws JsonProcessingException {
        var article = new Article();
        BeanUtils.copyProperties(articleDto,article);
        article.setCreateAt(LocalDateTime.now());
        article.setUpdateAt(LocalDateTime.now());
        article.setArticleStatus(ArticleStatus.NORMAL);
        return adminService.putArticle(article);
    }

    /**
     * 返回文章的预览，
     * @Author: HILL
     * @date: 2020/4/26 23:07
     *
     * @param pageInfoDto 分页信息
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/listPreview")
    public Result listArticlePreviews(PageInfoDto pageInfoDto, String category){
        return adminService.listArticlePreviews(pageInfoDto, category);
    }

    @RequestMapping("/get")
    public Result getArticle(@NotNull(message = "参数articleId不能为空") Integer articleId){
        return adminService.getArticle(articleId);
    }

    @RequestMapping("/update")
    public Result updateArticle(@NotNull(message = "参数articleId不能为空") Integer articleId,ArticleDto articleDto) throws JsonProcessingException {

        articleService.updateArticle(articleId,articleDto);
        return Result.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result deleteArticle(@NotNull(message = "参数articleId不能为空") Integer articleId) throws IOException {
        articleService.deleteArticle(articleId);
        return Result.genSuccessResult();
    }

    @RequestMapping("/uploadImg")
    public Result uploadArticleImg(@NotNull(message = "参数image不能为空") MultipartFile image) throws IOException {
        return bosService.uploadArticleImg(image);
    }

    /**
     * 添加需爬取的url
     * @Author: HILL
     * @date: 2020/4/28 15:53
     *
     * @param crawlUrl
     * @param category
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/addCrawlUrl")
    public Result addCrawlUrl(@NotNull(message = "参数crawlUrl不能为空") String crawlUrl,
                               String category){
        return adminService.addCrawlUrl(crawlUrl,category);
    }



}

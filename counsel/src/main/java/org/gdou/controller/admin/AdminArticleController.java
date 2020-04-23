package org.gdou.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.gdou.common.constant.article.ArticleStatus;
import org.gdou.common.result.Result;
import org.gdou.model.dto.article.ArticleDto;
import org.gdou.model.po.Article;
import org.gdou.service.impl.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/8
 **/
@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {


    AdminService adminService;

    public AdminArticleController(AdminService adminService) {
        this.adminService = adminService;
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


}

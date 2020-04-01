package org.gdou.controller;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
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



    @RequestMapping("/getArticleList")
    public Result getArticle(String category){
        return ResultGenerator.genSuccessResult(category);
    }



}

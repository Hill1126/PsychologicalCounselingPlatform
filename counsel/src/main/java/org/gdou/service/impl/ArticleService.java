package org.gdou.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.dao.ArticleMapper;
import org.gdou.dao.ConfigMapper;
import org.springframework.stereotype.Service;

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

    public ArticleService(ArticleMapper articleMapper, ConfigMapper configMapper) {
        this.articleMapper = articleMapper;
        this.configMapper = configMapper;
    }

    public Result getArticleCategory(){
        List<String> categoryList = configMapper.getValueByName("article", "category");
        return ResultGenerator.genSuccessResult(categoryList);
    }



}

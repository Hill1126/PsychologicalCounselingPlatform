package org.gdou.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.gdou.dao.ArticleMapper;
import org.springframework.stereotype.Service;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/1
 **/
@Service
@Slf4j
public class ArticleService {

    private ArticleMapper articleMapper;

    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }




}

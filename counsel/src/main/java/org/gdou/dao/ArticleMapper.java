package org.gdou.dao;

import org.gdou.model.po.Article;
import org.gdou.model.vo.article.ArticlePreviewVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    List<ArticlePreviewVo> getArticlePreview(String category,Integer status);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);
}
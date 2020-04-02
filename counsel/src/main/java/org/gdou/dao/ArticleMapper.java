package org.gdou.dao;

import org.gdou.model.po.Article;
import org.gdou.model.vo.ArticlePreviewVo;

import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    List<ArticlePreviewVo> getArticlePreview(String category);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);
}
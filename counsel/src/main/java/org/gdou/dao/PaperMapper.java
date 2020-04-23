package org.gdou.dao;

import org.gdou.model.po.Paper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaperMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Paper record);

    int insertSelective(Paper record);

    Paper selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Paper record);

    int updateByPrimaryKey(Paper record);

    List<Paper> listPapers(Integer userId);
}
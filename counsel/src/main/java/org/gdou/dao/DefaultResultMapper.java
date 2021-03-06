package org.gdou.dao;

import org.gdou.model.po.DefaultResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefaultResultMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DefaultResult record);

    int insertSelective(DefaultResult record);

    DefaultResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DefaultResult record);

    int updateByPrimaryKeyWithBLOBs(DefaultResult record);

    int updateByPrimaryKey(DefaultResult record);

    String getResultByScore(Integer paperId, Double totalScore);

    List<DefaultResult> listResultsByPaperId(Integer paperId);

    Integer scoreCheck(DefaultResult defaultResult);
}
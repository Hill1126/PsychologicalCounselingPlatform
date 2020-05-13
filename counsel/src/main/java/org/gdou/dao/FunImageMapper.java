package org.gdou.dao;

import org.gdou.model.po.FunImage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunImageMapper {
    int deleteByPrimaryKey(Integer funImageId);

    int insert(FunImage record);

    int insertSelective(FunImage record);

    FunImage selectByPrimaryKey(Integer funImageId);

    int updateByPrimaryKeySelective(FunImage record);

    int updateByPrimaryKey(FunImage record);

    List<FunImage> listFunImages();
}
package org.gdou.dao;

import org.apache.ibatis.annotations.Param;
import org.gdou.model.po.Oauths;
import org.gdou.model.po.example.OauthsExample;

import java.util.List;

public interface OauthsMapper {


    long countByExample(OauthsExample example);

    int deleteByExample(OauthsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Oauths record);

    int insertSelective(Oauths record);

    List<Oauths> selectByExample(OauthsExample example);

    Oauths selectByPrimaryKey(Integer id);

    int ifExitsOauthId(String oauthId);

    int updateByExampleSelective(@Param("record") Oauths record, @Param("example") OauthsExample example);

    int updateByExample(@Param("record") Oauths record, @Param("example") OauthsExample example);

    int updateByPrimaryKeySelective(Oauths record);

    int updateByPrimaryKey(Oauths record);
}
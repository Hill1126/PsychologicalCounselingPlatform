package org.gdou.dao;

import org.gdou.model.po.Config;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Config record);

    int insertSelective(Config record);

    Config selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKey(Config record);

    List<String> getValueByName(String scope,String name);
}
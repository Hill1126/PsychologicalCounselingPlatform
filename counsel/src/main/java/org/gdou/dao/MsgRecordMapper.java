package org.gdou.dao;

import org.apache.ibatis.annotations.Param;
import org.gdou.model.po.MsgRecord;
import org.gdou.model.po.example.MsgRecordExample;

import java.util.List;

public interface MsgRecordMapper {
    long countByExample(MsgRecordExample example);

    int deleteByExample(MsgRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsgRecord record);

    int insertSelective(MsgRecord record);

    List<MsgRecord> selectByExample(MsgRecordExample example);

    MsgRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsgRecord record, @Param("example") MsgRecordExample example);

    int updateByExample(@Param("record") MsgRecord record, @Param("example") MsgRecordExample example);

    int updateByPrimaryKeySelective(MsgRecord record);

    int updateByPrimaryKey(MsgRecord record);
}
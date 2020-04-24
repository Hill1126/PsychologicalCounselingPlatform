package org.gdou.dao;

import org.gdou.model.po.TestRecord;
import org.gdou.model.vo.TestRecordVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestRecord record);

    int insertSelective(TestRecord record);

    TestRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestRecord record);

    int updateByPrimaryKeyWithBLOBs(TestRecord record);

    int updateByPrimaryKey(TestRecord record);

    List<TestRecordVo> listRecords(Integer userId);
}
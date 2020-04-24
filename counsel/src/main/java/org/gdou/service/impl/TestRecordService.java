package org.gdou.service.impl;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.result.Result;
import org.gdou.dao.TestRecordMapper;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.vo.TestRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/24
 **/
@Service
@Slf4j
public class TestRecordService {

    @Autowired
    private TestRecordMapper testRecordMapper;

    /**
     * 根据所给的用户id查询当前用户所测试的记录
     * @Author: HILL
     * @date: 2020/4/24 20:41
     *
     * @param pageInfoDto 分页信息
     * @param userId 用户id
     * @return: org.gdou.common.result.Result 返回带有TestRecordVo集合的result
    **/
    public Result listRecords(PageInfoDto pageInfoDto, Integer userId) {
        log.debug("执行方法【listRecords】,分页信息为：{}",pageInfoDto.toString());
        PageHelper.startPage(pageInfoDto.getPageNum(),pageInfoDto.getPageSize());
        List<TestRecordVo> testRecordVos =  testRecordMapper.listRecords(userId);
        log.info("查询用户id【{}】的测试成绩，总条数为【{}】",userId,testRecordVos);
        return Result.genSuccessResult(testRecordVos);
    }
}

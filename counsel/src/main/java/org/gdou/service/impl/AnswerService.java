package org.gdou.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.CommonDataStatus;
import org.gdou.common.result.Result;
import org.gdou.dao.AnswerMapper;
import org.gdou.model.po.Answer;
import org.springframework.stereotype.Service;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/24
 **/
@Service
@Slf4j
public class AnswerService {

    public AnswerService(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

    private AnswerMapper answerMapper;

    /**
     * 根据所给的answer对象插入到数据库中
     * @Author: HILL
     * @date: 2020/4/24 13:55
     *
     * @param answer 要插入的答案
     * @return: org.gdou.common.result.Result 带有插入后的answer的result
    **/
    public Result addAnswer(Answer answer) {
        answerMapper.insert(answer);
        log.info("问题id:【{}】新增一个答案，内容为：{}，分值为【{}】",answer.getQuestionId(),
                  answer.getAnswerValue(),answer.getAnswerScore());
        return Result.genSuccessResult(answer);
    }

    /**
     * 根据所给数据更新答案，不能修改所属的问题。
     * @Author: HILL
     * @date: 2020/4/24 14:15
     *
     * @param answer 要修改的数据
     * @return: org.gdou.common.result.Result
    **/
    public Result updateAnswer(Answer answer) {
        answerMapper.updateByPrimaryKeySelective(answer);
        log.info("答案id【{}】已被{}",answer.getId(),
                answer.getAnswerStatus()== CommonDataStatus.DELETE?"删除":"更新");
        return Result.genSuccessResult();
    }


}

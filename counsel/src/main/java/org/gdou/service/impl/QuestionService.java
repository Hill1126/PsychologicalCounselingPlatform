package org.gdou.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.result.Result;
import org.gdou.dao.QuestionMapper;
import org.gdou.model.po.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/22
 **/
@Service
@Slf4j
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 给某个试卷添加问题
     * @Author: HILL
     * @date: 2020/4/22 16:53
     *
     * @param question 要添加的问题
     * @return: org.gdou.common.result.Result
    **/
    public Result addQuestion(Question question) {
        questionMapper.insert(question);
        log.info("用户【{}】添加为试卷【】添加了一个问题，标题为【】",question.getCreatBy()
                ,question.getPaperId(),question.getQuestionTitle());
        return Result.genSuccessResult();
    }
}

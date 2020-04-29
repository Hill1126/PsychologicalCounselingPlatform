package org.gdou.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.result.Result;
import org.gdou.dao.AnswerMapper;
import org.gdou.dao.QuestionMapper;
import org.gdou.model.dto.paper.question.QuestionDto;
import org.gdou.model.po.Answer;
import org.gdou.model.po.Question;
import org.gdou.model.po.User;
import org.gdou.model.vo.paper.QuestionsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private AnswerMapper answerMapper;
    /**
     * 给某个试卷添加问题
     * @Author: HILL
     * @date: 2020/4/22 16:53
     *
     * @param question 要添加的问题
     * @return: org.gdou.common.result.Result 包含question的result。
    **/
    public Result addQuestion(Question question) {
        questionMapper.insert(question);
        log.info("用户【{}】添加为试卷【{}】添加了一个问题，标题为【{}】",question.getCreatBy()
                ,question.getPaperId(),question.getQuestionTitle());
        return Result.genSuccessResult(question);
    }

    /**
     * 校验当前用户是否能够修改该试题，同时完成试题的更新
     * @Author: HILL
     * @date: 2020/4/23 14:47
     *
     * @param questionDto
     * @param user
     * @return: org.gdou.common.result.Result
    **/
    public Result updateQuestion(QuestionDto questionDto, User user) {
        int i = questionMapper.checkUserAuthority(questionDto.getQuestionId(), user.getId());
        if (i==0){
            log.info("用户修改试题【{}】失败，没有修改权限。用户名为【{}】",questionDto.getQuestionId(),user.getName());
            return Result.genFailResult("您没有权限修改当前的题目");
        }else {
            var question = new Question();
            BeanUtils.copyProperties(questionDto,question);
            question.setId(questionDto.getQuestionId());
            questionMapper.updateByPrimaryKeySelective(question);
            log.info("用户【{}】修改问题成功，问题详情为:{}",user.getName()
                    ,question.toString());
        }
        return Result.genSuccessResult();
    }

    /**
     * 根据试卷id，获取属于该试卷的题目集合，以及题目拥有的答案
     * @Author: HILL
     * @date: 2020/4/23 15:38
     *
     * @param paperId
     * @return: org.gdou.common.result.Result
    **/
    public Result listQuestions(Integer paperId) {
        //先获取试卷的所有问题。
        var questionVoList = questionMapper.listQuestions(paperId);
        //生成问题id的集合，根据问题id查找对应的答案。
        List<Integer> ids = questionVoList.stream().map(QuestionsVo::getQuestionId).collect(Collectors.toList());
        //若试卷有问题，那么进行答案的获取
        List<Answer> answerList;
        if (ids!=null && ids.size()>0){
            answerList = answerMapper.listAnswers(ids);
            var collect = answerList.stream().
                    collect(Collectors.groupingBy(Answer::getQuestionId));
            //根据id匹配答案与题目
            questionVoList.forEach((vo) -> {
                vo.setAnswerList(collect.get(vo.getQuestionId()));
            });
        }

        return Result.genSuccessResult(questionVoList);

    }
}

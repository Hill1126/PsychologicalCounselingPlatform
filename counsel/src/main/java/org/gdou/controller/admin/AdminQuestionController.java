package org.gdou.controller.admin;

import org.gdou.common.constant.CommonDataStatus;
import org.gdou.common.result.Result;
import org.gdou.common.utils.UserUtils;
import org.gdou.model.dto.paper.question.CreateAnswerDto;
import org.gdou.model.dto.paper.question.QuestionDto;
import org.gdou.model.dto.paper.question.UpdateAnswerDto;
import org.gdou.model.po.Answer;
import org.gdou.model.po.Question;
import org.gdou.model.po.User;
import org.gdou.service.impl.AnswerService;
import org.gdou.service.impl.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/22
 **/
@RestController
@RequestMapping("/admin/question")
@Validated
public class AdminQuestionController {

    public AdminQuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    private QuestionService questionService;
    private AnswerService answerService;

    /**
     * 根据所给出的试卷id，为该试卷设置一个问题。
     * @Author: HILL
     * @date: 2020/4/23 14:31
     *
     * @param paperId
     * @param questionTitle
     * @param request
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping(value = "/creat",method = RequestMethod.POST)
    public Result addQuestion(@NotNull(message = "参数paperId不能为空") Integer paperId,
                              @NotNull(message = "参数questionTitle不能为空") String questionTitle,
                              HttpServletRequest request){
        var user = UserUtils.getUserInRequest(request);
        //构建问题对象
        Question question = new Question();
        question.setCreatAt(LocalDateTime.now());
        question.setCreatBy(user.getName());
        question.setPaperId(paperId);
        question.setQuestionTitle(questionTitle);
        question.setQuestionStatus(CommonDataStatus.PUBLIC);
        return questionService.addQuestion(question);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateQuestion(@Validated QuestionDto questionDto,HttpServletRequest request){
        User user = UserUtils.getUserInRequest(request);
        return questionService.updateQuestion(questionDto,user);
    }

    @RequestMapping(value = "/list")
    public Result listQuestions( @NotNull(message = "参数paperId不能为空") Integer paperId){
        return questionService.listQuestions(paperId);
    }

    /**
     *
     * @Author: HILL
     * @date: 2020/4/24 13:46
     *
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping(value = "/addAnswer",method = RequestMethod.POST)
    public Result addAnswer(@Validated CreateAnswerDto answerDto){
        var answer = new Answer();
        BeanUtils.copyProperties(answerDto,answer);
        //新建答案默认正常状态
        answer.setAnswerStatus(CommonDataStatus.PUBLIC);
        return answerService.addAnswer(answer);
    }

    /**
     * 根据传入的答案对象修改，同时答案id不能为空，指定的问题不能修改。
     * @Author: HILL
     * @date: 2020/4/24 14:10
     *
     * @param answerDto 要修改的答案
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping(value = "/updateAnswer",method = RequestMethod.POST)
    public Result updateAnswer(@Validated UpdateAnswerDto answerDto){
        var answer = new Answer();
        BeanUtils.copyProperties(answerDto,answer);
        answer.setId(answerDto.getAnswerId());
        return answerService.updateAnswer(answer);
    }



}

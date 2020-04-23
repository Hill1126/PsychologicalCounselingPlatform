package org.gdou.controller.admin;

import org.gdou.common.constant.CommonDataStatus;
import org.gdou.common.result.Result;
import org.gdou.common.utils.UserUtils;
import org.gdou.model.po.Question;
import org.gdou.service.impl.QuestionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/22
 **/
@RestController
@RequestMapping("/admin/question")
public class AdminQuestionController {

    public AdminQuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }


    private QuestionService questionService;

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public Result addQuestion(Integer paperId, String questionTitle, HttpServletRequest request){

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
}

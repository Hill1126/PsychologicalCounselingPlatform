package org.gdou.controller.admin;

import org.gdou.common.constant.CommonDataStatus;
import org.gdou.common.result.Result;
import org.gdou.common.utils.UserUtils;
import org.gdou.model.dto.paper.PaperDto;
import org.gdou.model.po.DefaultResult;
import org.gdou.model.po.Paper;
import org.gdou.service.impl.DefaultResultService;
import org.gdou.service.impl.PaperService;
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
@Validated
@RequestMapping("/admin/paper")
public class AdminPaperController {

    public AdminPaperController(PaperService paperService, QuestionService questionService, DefaultResultService defaultResultService) {
        this.paperService = paperService;
        this.questionService = questionService;
        this.defaultResultService = defaultResultService;
    }

    private PaperService paperService;
    private QuestionService questionService;
    private DefaultResultService defaultResultService;

    /**
     * 根据相关信息添加试卷
     * @Author: HILL
     * @date: 2020/4/22 22:54
     *
     * @param paperDto 描述信息
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping(value = "/creat",method = RequestMethod.POST)
    public Result creatPaper(PaperDto paperDto, HttpServletRequest request){
        var user = UserUtils.getUserInRequest(request);
        var paper = new Paper();
        BeanUtils.copyProperties(paperDto,paper);
        paper.setCreatUserId(user.getId());
        paper.setCreatAt(LocalDateTime.now());
        paper.setPaperStatus(CommonDataStatus.EDIT);
        return  paperService.creatPaper(paper);
    }

    /**
     * 根据前端传入数据，更新相应的paper字段
     * @Author: HILL
     * @date: 2020/4/22 22:52
     *
     * @param paperId 试卷id
     * @param paperDto 试卷需要更新的信息更新
     * @return: org.gdou.common.result.Result
     **/
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateStatus(@NotNull Integer paperId, PaperDto paperDto){
        return  paperService.updatePaper(paperId,paperDto);
    }

    @RequestMapping("/get")
    public Result getByUser(HttpServletRequest request){
        Integer userId = UserUtils.getUserInRequest(request).getId();
        return paperService.listPapers(userId);
    }


    @RequestMapping(value = "/result",method = RequestMethod.POST)
    public Result addDefaultResult(@Validated DefaultResult defaultResult){
        //判断是否是更新，删除等情况
        if (defaultResult.getId()!=null){
            return defaultResultService.updateResult(defaultResult);
        }
        return defaultResultService.addDefaultResult(defaultResult);
    }




}

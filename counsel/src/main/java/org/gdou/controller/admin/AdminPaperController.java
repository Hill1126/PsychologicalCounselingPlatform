package org.gdou.controller.admin;

import org.gdou.common.annotaions.RoleControl;
import org.gdou.common.constant.CommonDataStatus;
import org.gdou.common.constant.user.UserType;
import org.gdou.common.result.Result;
import org.gdou.common.utils.UserUtils;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.dto.paper.DefaultResultDto;
import org.gdou.model.dto.paper.PaperDto;
import org.gdou.model.po.DefaultResult;
import org.gdou.model.po.Paper;
import org.gdou.service.impl.BosService;
import org.gdou.service.impl.DefaultResultService;
import org.gdou.service.impl.PaperService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/22
 **/
@RestController
@Validated
@RequestMapping("/admin/paper")
@RoleControl(userType = UserType.TEACHER)
public class AdminPaperController {

    public AdminPaperController(PaperService paperService, DefaultResultService defaultResultService, BosService bosService) {
        this.paperService = paperService;
        this.defaultResultService = defaultResultService;
        this.bosService = bosService;
    }

    private PaperService paperService;
    private DefaultResultService defaultResultService;
    private BosService bosService;

    /**
     * 根据相关信息添加试卷
     * @Author: HILL
     * @date: 2020/4/22 22:54
     *
     * @param paperDto 描述信息
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping(value = "/creat",method = RequestMethod.POST)
    public Result creatPaper( PaperDto paperDto, HttpServletRequest request){
        var user = UserUtils.getUserInRequest(request);
        var paper = new Paper();

        BeanUtils.copyProperties(paperDto,paper);
        paper.setCreatUserId(user.getId());
        paper.setCreatAt(LocalDateTime.now());
        paper.setPaperStatus(CommonDataStatus.EDIT);
        return  paperService.creatPaper(paper);
    }

    @RequestMapping(value = "/setCover",method = RequestMethod.POST)
    public Result setCover(@NotNull(message = "参数paperId不能为空") Integer paperId,
                           @NotNull(message = "上传文件coverImg不能为空") MultipartFile coverImg) throws IOException {
        Result result = bosService.uploadArticleImg(coverImg);
        Paper paper = new Paper();
        paper.setId(paperId);
        paper.setCoverUrl(result.getData().toString());
        paperService.updatePaper(paper);
        return result;
    }


    /**
     * 根据前端传入数据，更新相应的paper字段
     * @Author: HILL
     * @date: 2020/4/22 22:52
     *
     * @param paperDto 试卷需要更新的信息更新
     * @return: org.gdou.common.result.Result
     **/
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateStatus(@Validated PaperDto paperDto){
        var paper = new Paper();
        BeanUtils.copyProperties(paperDto,paper);
        return  paperService.updatePaper(paper);
    }

    /**
     * 将试卷发布到用户端
     * @Author: HILL
     * @date: 2020/5/1 10:22
     *
     * @param paperId 要发布的试卷
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/public")
    public Result publicPaper(@NotNull(message = "参数paperId不能为空") Integer paperId){
        var paper = new Paper();
        paper.setId(paperId);
        paper.setPaperStatus(CommonDataStatus.PUBLIC);
        paperService.updatePaper(paper);
        return Result.genSuccessResult();
    }

    @RequestMapping("/list")
    public Result listPapers(PageInfoDto pageInfoDto,HttpServletRequest request){
        Integer userId = UserUtils.getUserInRequest(request).getId();
        return paperService.listPapers(pageInfoDto,userId);
    }

    /**
     * 将paper更新为删除状态
     * @Author: HILL
     * @date: 2020/4/29 14:42
     *
     * @param paperId
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/delete")
    public Result deletePaper(@NotNull(message = "参数paperId不能为空") Integer paperId){
        var paper = new Paper();
        paper.setId(paperId);
        paper.setPaperStatus(CommonDataStatus.DELETE);
        paperService.updatePaper(paper);
        return Result.genSuccessResult();
    }


    @RequestMapping(value = "/addResult",method = RequestMethod.POST)
    public Result addDefaultResult(@Validated DefaultResult defaultResult){
        if (defaultResult.getScoreStart().compareTo(defaultResult.getScoreEnd())<0){
            return Result.genFailResult("参数范围不正确");
        }
        return defaultResultService.addDefaultResult(defaultResult);
    }

    @RequestMapping(value = "/updateResult",method = RequestMethod.POST)
    public Result updateDefaultResult(@NotNull(message = "参数resultId不能为空") Integer resultId,
                                      DefaultResultDto defaultResultDto){
        var defaultResult = new DefaultResult();
        BeanUtils.copyProperties(defaultResultDto,defaultResult);
        defaultResult.setId(resultId);
        return defaultResultService.updateResult(defaultResult);
    }

    @RequestMapping("/listResult")
    public Result listDefaultResult(@NotNull(message = "参数paperId不能为空") Integer paperId){
        return defaultResultService.listResults(paperId);
    }

    @RequestMapping("/deleteResult")
    public Result deleteResult(@NotNull(message = "参数resultId不能为空") Integer resultId){
        return defaultResultService.deleteResult(resultId);
    }

}

package org.gdou.controller;

import org.gdou.common.result.Result;
import org.gdou.common.utils.UserUtils;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.po.User;
import org.gdou.service.impl.PaperService;
import org.gdou.service.impl.TestRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/24
 **/
@RestController
@RequestMapping("/paper")
public class PaperController {


    PaperService paperService;
    TestRecordService testRecordService;

    public PaperController(PaperService paperService, TestRecordService testRecordService) {
        this.paperService = paperService;
        this.testRecordService = testRecordService;
    }

    /**
     * 根据分页信息返回，试题的简略信息
     * @Author: HILL
     * @date: 2020/4/24 15:25
     *
     * @param pageInfoDto
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/previewList")
    public Result listPreviews(PageInfoDto pageInfoDto){
        return paperService.listPreviews(pageInfoDto);
    }

    @RequestMapping("/get")
    public Result getPaper(@NotNull(message = "参数paperId不能为空") Integer paperId){
        return paperService.getPaper(paperId);
    }


    @RequestMapping(value = "/commit",method = RequestMethod.POST)
    public Result getResult(@NotNull(message = "参数paperId不能为空")Integer paperId,
                            @NotNull(message = "参数totalScore不能为空")Double totalScore,
                            HttpServletRequest request){
        User user = UserUtils.getUserInRequest(request);
        return paperService.commit(paperId,totalScore,user.getId());
    }

    @RequestMapping(value = "/record")
    public Result listRecord(PageInfoDto pageInfoDto,HttpServletRequest request){
        User user = UserUtils.getUserInRequest(request);
        return testRecordService.listRecords(pageInfoDto,user.getId());
    }


}

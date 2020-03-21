package org.gdou.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.service.impl.CounselService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/19
 **/
@RestController
@RequestMapping("/counsel")
@Slf4j
public class CounselController {


    public CounselController(CounselService counselService) {
        this.counselService = counselService;
    }

    private CounselService counselService;

    @RequestMapping("/teacherList")
    public Result getTeacherList(@RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "5") int pageSize,
                                 @RequestParam(defaultValue = "false") boolean isOrderByDesc){
        if (pageNum<1 || pageSize<1){
            return ResultGenerator.genFailResult("参数非法,请修改后尝试！");
        }
        PageInfo teacherList = counselService.getTeacherList(pageNum, pageSize, isOrderByDesc);
        return ResultGenerator.genSuccessResult(teacherList);

    }





}

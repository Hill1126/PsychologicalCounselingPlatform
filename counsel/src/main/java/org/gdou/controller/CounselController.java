package org.gdou.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.model.dto.counsel.MakeAppointmentDto;
import org.gdou.model.po.User;
import org.gdou.service.impl.CounselService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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

    /**
     * 根据分页参数返回相应的可预约老师列表
     * @Author: HILL
     * @date: 2020/3/21 15:53
     *
     * @param pageNum 当前页面
     * @param pageSize 页面的数据量
     * @param isHot 是否将较多人预约的老师排在前列。
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/teacherList")
    public Result getTeacherList(@RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "5") int pageSize,
                                 @RequestParam(defaultValue = "false") boolean isHot){
        if (pageNum<1 || pageSize<1){
            return ResultGenerator.genFailResult("参数非法,请修改后尝试！");
        }
        PageInfo teacherList = counselService.getTeacherList(pageNum, pageSize, isHot);
        return ResultGenerator.genSuccessResult(teacherList);

    }


    @RequestMapping("/availableTime")
    public Result getAppointmentTime(int teacherId){
        return counselService.getAppointmentTimeById(teacherId);
    }

    @RequestMapping("/appointment")
    public Result makeAppointment(MakeAppointmentDto makeAppointmentDto, HttpSession session){
        var attribute = (User)session.getAttribute(ProjectConstant.USER_SESSION_KEY);
        makeAppointmentDto.setStudentId(attribute.getId());
        return counselService.makeAppointment(makeAppointmentDto);
    }




   /* @RequestMapping("/myAppointment")
    public Result getWorkOrderById(HttpSession session){
        var user = (User)session.getAttribute(ProjectConstant.USER_SESSION_KEY);
        List<>counselService.getWorkOrderByID(user.getId());
    }
*/



}

package org.gdou.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.constant.user.UserType;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.model.bo.MakeAppointmentBO;
import org.gdou.model.dto.counsel.MakeAppointmentDto;
import org.gdou.model.po.User;
import org.gdou.model.qo.CounselHistoryQo;
import org.gdou.service.impl.CounselService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
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

    /**
     * 根据老师id查询老师能预约的时间
     * @Author: HILL
     * @date: 2020/3/25 14:47
     *
     * @param teacherId 老师id
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/availableTime")
    public Result getAppointmentTime(int teacherId){
        return counselService.getAppointmentTimeById(teacherId);
    }

    /**
     * 传入相应的信息进行预约
     * @Author: HILL
     * @date: 2020/3/25 14:48
     *
     * @param makeAppointmentDto 包括老师id、预约日期时间等
     * @param session 获取当前登录用户的信息
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/appointment")
    public Result makeAppointment(@Validated MakeAppointmentDto makeAppointmentDto, HttpSession session){
        var attribute = (User)session.getAttribute(ProjectConstant.USER_SESSION_KEY);
        MakeAppointmentBO bo = new MakeAppointmentBO();
        BeanUtils.copyProperties(makeAppointmentDto,bo);
        var appointmentDateTime = makeAppointmentDto.getAppointmentDateTime();
        bo.setAppointmentTime(appointmentDateTime.toLocalTime());
        bo.setAppointmentDate(appointmentDateTime.toLocalDate());
        bo.setStudentId(attribute.getId());
        bo.setStudentName(attribute.getName());
        return counselService.makeAppointment(bo);
    }

    /**
     * 查看当前登录用户进行中的预约
     * @Author: HILL
     * @date: 2020/3/25 14:49
     *
     * @param session
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/myAppointment")
    public Result getWorkOrderById(HttpSession session){
        var user = (User)session.getAttribute(ProjectConstant.USER_SESSION_KEY);
        return counselService.getWorkOrderByID(user.getId());
    }

    /**
     * 根据当前登录用户查询已完成或已关闭的历史咨询
     * @Author: HILL
     * @date: 2020/3/25 15:20
     *
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/history")
    public Result getMyCounselHistory(HttpSession session){
        var user = (User)session.getAttribute(ProjectConstant.USER_SESSION_KEY);
        CounselHistoryQo historyQo = new CounselHistoryQo();
        if (user.getUserType().equals(UserType.STUDENT)){
            historyQo.setStudentId(user.getId());
        }else if (user.getUserType().equals(UserType.TEACHER)){
            historyQo.setTeacherId(user.getId());
        }
        return counselService.getMyCounselHistory(historyQo);
    }




}

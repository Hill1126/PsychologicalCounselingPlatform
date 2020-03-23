package org.gdou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.chat.TimeQuantum;
import org.gdou.common.constant.chat.WorkOrderStatus;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.dao.UserMapper;
import org.gdou.dao.WorkOrderMapper;
import org.gdou.model.bo.AppointmentTimeBo;
import org.gdou.model.dto.counsel.MakeAppointmentDto;
import org.gdou.model.po.WorkOrder;
import org.gdou.model.qo.AvailableTimeQo;
import org.gdou.model.qo.TeacherChatQo;
import org.gdou.model.vo.AppointmentTimeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/21
 **/
@Service
@Slf4j
public class CounselService {

    private UserMapper userMapper;
    private WorkOrderMapper workOrderMapper;

    public CounselService(UserMapper userMapper, WorkOrderMapper workOrderMapper) {
        this.userMapper = userMapper;
        this.workOrderMapper = workOrderMapper;
    }


    public PageInfo getTeacherList(int pageNum, int pageSize, boolean isHot){
        PageHelper.startPage(pageNum,pageSize);
        //查找标识为老师且当前预约时间比较多的的用户。
        TeacherChatQo teacherChatQo = TeacherChatQo.quicklyBuild();
        teacherChatQo.setHot(isHot);
        return PageInfo.of(userMapper.selectAppointmentTeacher(teacherChatQo));
    }

    public Result getAppointmentTimeById(Integer id){
        LocalDate now = LocalDate.now();
        List<AppointmentTimeBo> timeList = workOrderMapper.getAppointmentById(new AvailableTimeQo(id,
                now,now.plusDays(1)));
        //分离出今天与明天已预约的时间
        HashMap<LocalTime, Boolean> todayListMap = TimeQuantum.getDefaultTimeMap();
        HashMap<LocalTime, Boolean> tomorrowTimeMap = TimeQuantum.getDefaultTimeMap();
        timeList.forEach((bo)->{
            //根据日期设定某个时间段已经被预约
            if(bo.getAppointmentDate().equals(now)){
                todayListMap.put(bo.getAppointmentTime(),true);
            }else{
                tomorrowTimeMap.put(bo.getAppointmentTime(),true);
            }
        });
        return ResultGenerator.genSuccessResult(new AppointmentTimeVo(todayListMap,tomorrowTimeMap));

    }

    public Result makeAppointment(MakeAppointmentDto dto){
        //根据dto构建WorkOrder对象
        WorkOrder workOrder = new WorkOrder();
        BeanUtils.copyProperties(dto,workOrder);
        workOrder.setCreateAt(LocalDateTime.now());
        workOrder.setStatus(WorkOrderStatus.READY);
        synchronized (this){
            //判断当前时间段是否已经被预约
            if (workOrderMapper.checkAppointmentBeforeInsert(dto)>0){
                return ResultGenerator.genFailResult("该时间段已被预约");
            }
            workOrderMapper.insert(workOrder);
        }

        return ResultGenerator.genSuccessResult();

    }



}

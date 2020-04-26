package org.gdou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.constant.chat.TimeQuantum;
import org.gdou.common.constant.chat.WorkOrderStatus;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.common.utils.RedisUtil;
import org.gdou.dao.MsgRecordMapper;
import org.gdou.dao.UserMapper;
import org.gdou.dao.WorkOrderMapper;
import org.gdou.model.bo.AppointmentTimeBo;
import org.gdou.model.bo.MakeAppointmentBO;
import org.gdou.model.bo.TodoCounselBo;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.po.MsgRecord;
import org.gdou.model.po.WorkOrder;
import org.gdou.model.po.example.MsgRecordExample;
import org.gdou.model.qo.AvailableTimeQo;
import org.gdou.model.qo.CounselHistoryQo;
import org.gdou.model.qo.TeacherChatQo;
import org.gdou.model.vo.AppointmentTimeVo;
import org.gdou.model.vo.CounselHistoryVo;
import org.gdou.model.vo.TodoListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private MsgRecordMapper msgRecordMapper;
    private RedisUtil redisUtil;

    public CounselService(UserMapper userMapper, WorkOrderMapper workOrderMapper, MsgRecordMapper msgRecordMapper, RedisUtil redisUtil) {
        this.userMapper = userMapper;
        this.workOrderMapper = workOrderMapper;
        this.msgRecordMapper = msgRecordMapper;
        this.redisUtil = redisUtil;
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
        Map<LocalTime, Boolean> todayListMap = TimeQuantum.getDefaultTimeMap();
        Map<LocalTime, Boolean> tomorrowTimeMap = TimeQuantum.getDefaultTimeMap();
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

    public Result makeAppointment(MakeAppointmentBO bo){
        //根据dto构建WorkOrder对象
        WorkOrder workOrder = new WorkOrder();
        BeanUtils.copyProperties(bo,workOrder);
        workOrder.setCreateAt(LocalDateTime.now());
        workOrder.setStatus(WorkOrderStatus.READY);
        //设置本次工单的标题
        workOrder.setTitle(String.format("%s与%s在%s的咨询",bo.getStudentName(),bo.getTeacherName(),bo.getAppointmentDate()));
        synchronized (this){
            //判断当前时间段是否已经被预约
            if (workOrderMapper.checkAppointmentBeforeInsert(bo)>0){
                return ResultGenerator.genFailResult("该时间段已被预约");
            }
            workOrderMapper.insert(workOrder);
        }

        return ResultGenerator.genSuccessResult("预约成功");

    }


    /**
     * 根据id查询当前惊醒中的咨询工单
     * @Author: HILL
     * @date: 2020/3/24 22:17
     *
     * @param id 要查询的id
     * @return: org.gdou.common.result.Result
    **/
    public Result getWorkOrderByID(Integer id) {
        return ResultGenerator.genSuccessResult(workOrderMapper.getMyAppointmentById(id));
    }

    /**
     * 根据当前用户的id查询相应的历史咨询数据
     * @Author: HILL
     * @date: 2020/3/25 17:16
     *
     * @param qo
     * @return: org.gdou.common.result.Result
    **/
    public Result getMyCounselHistory(CounselHistoryQo qo){
        List<CounselHistoryVo> historyList = workOrderMapper.getCounselHistory(qo);
        return ResultGenerator.genSuccessResult(historyList);
    }

    public Result getMsgRecord(Integer workOrderId, PageInfoDto pageInfoDto) {
        MsgRecordExample recordExample = new MsgRecordExample();
        recordExample.createCriteria().andOrderIdEqualTo(workOrderId);
        //根据发送时间的降序排列
        recordExample.setOrderByClause("time desc");
        PageHelper.startPage(pageInfoDto.getPageNum(),pageInfoDto.getPageSize());
        List<MsgRecord> msgRecords = msgRecordMapper.selectByExample(recordExample);
        return ResultGenerator.genSuccessResult(PageInfo.of(msgRecords));
    }

    /**
     * 返回老师当前进行中的预约单
     * 同时区分今日和明日
     * @Author: HILL
     * @date: 2020/3/26 22:23
     *
     * @param id 老师的id
     * @return: org.gdou.common.result.Result
    **/
    public Result getTodoCounsel(Integer id) {
        LocalDate now = LocalDate.now();
        AvailableTimeQo qo = new AvailableTimeQo(id,now,now.plusDays(1));
        List<TodoCounselBo> todoCounselList = workOrderMapper.getTodoCounsel(qo);
        var todayCounsel = new ArrayList<TodoCounselBo>();
        var tomorrowCounsel = new ArrayList<TodoCounselBo>();
        //将获取的vo按照日期分为两个List
        todoCounselList.forEach((counselVo)->{
            if (counselVo.getAppointmentDate().equals(now)){
                todayCounsel.add(counselVo);
            }else{
                tomorrowCounsel.add(counselVo);
            }
        });
        todoCounselList.clear();
        return ResultGenerator.genSuccessResult(new TodoListVo(todayCounsel,tomorrowCounsel));

    }

    /**
     * 异步插入消息记录到表中,并更新redis当前咨询工单的活跃时间
     * @Author: HILL
     * @date: 2020/3/27 21:47
     *
     * @param msgRecord
     * @return: void
    **/
    @Async("msgInsertExecutor")
    public void insertMsgRecord(MsgRecord msgRecord) {
        msgRecordMapper.insert(msgRecord);
        //更新redis的记录时间
        redisUtil.setEx(ProjectConstant.ORDER_KEY+msgRecord.getOrderId(),
                "1",ProjectConstant.ORDER_KEY_EXPIRE);
    }
}

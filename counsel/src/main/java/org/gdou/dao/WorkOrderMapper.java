package org.gdou.dao;

import org.apache.ibatis.annotations.Param;
import org.gdou.model.bo.AppointmentTimeBo;
import org.gdou.model.bo.MakeAppointmentBO;
import org.gdou.model.bo.TodoCounselBo;
import org.gdou.model.po.WorkOrder;
import org.gdou.model.po.example.WorkOrderExample;
import org.gdou.model.qo.AvailableTimeQo;
import org.gdou.model.qo.CounselHistoryQo;
import org.gdou.model.qo.TimeQo;
import org.gdou.model.vo.CounselHistoryVo;
import org.gdou.model.vo.MyAppointmentVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOrderMapper {
    long countByExample(WorkOrderExample example);

    int deleteByExample(WorkOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WorkOrder record);

    int insertSelective(WorkOrder record);

    List<WorkOrder> selectByExample(WorkOrderExample example);

    WorkOrder selectByPrimaryKey(Integer id);

    List<AppointmentTimeBo> getAppointmentById(AvailableTimeQo availableTimeQo);

    List<MyAppointmentVo> getMyAppointmentById(Integer id);

    List<CounselHistoryVo> getCounselHistory(CounselHistoryQo counselHistoryQo);

    int checkAppointmentBeforeInsert(MakeAppointmentBO dto);

    int updateByExampleSelective(@Param("record") WorkOrder record, @Param("example") WorkOrderExample example);

    int updateByExample(@Param("record") WorkOrder record, @Param("example") WorkOrderExample example);

    int updateByPrimaryKeySelective(WorkOrder record);

    int updateByPrimaryKey(WorkOrder record);

    void updateStatusByIds(List<Integer> ids);


    List<TodoCounselBo> getTodoCounsel(AvailableTimeQo availableTimeQoo);

    List<Integer> getTimeUpOrderId(TimeQo timeQo);


    WorkOrder getByOrderId(Integer orderId, Integer orderStatus);
}
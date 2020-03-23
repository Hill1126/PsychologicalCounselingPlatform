package org.gdou.dao;

import org.apache.ibatis.annotations.Param;
import org.gdou.model.bo.AppointmentTimeBo;
import org.gdou.model.dto.counsel.MakeAppointmentDto;
import org.gdou.model.po.WorkOrder;
import org.gdou.model.po.example.WorkOrderExample;
import org.gdou.model.qo.AvailableTimeQo;

import java.util.List;

public interface WorkOrderMapper {
    long countByExample(WorkOrderExample example);

    int deleteByExample(WorkOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WorkOrder record);

    int insertSelective(WorkOrder record);

    List<WorkOrder> selectByExample(WorkOrderExample example);

    WorkOrder selectByPrimaryKey(Integer id);

    List<AppointmentTimeBo> getAppointmentById(AvailableTimeQo availableTimeQo);

    int checkAppointmentBeforeInsert(MakeAppointmentDto dto);

    int updateByExampleSelective(@Param("record") WorkOrder record, @Param("example") WorkOrderExample example);

    int updateByExample(@Param("record") WorkOrder record, @Param("example") WorkOrderExample example);

    int updateByPrimaryKeySelective(WorkOrder record);

    int updateByPrimaryKey(WorkOrder record);
}
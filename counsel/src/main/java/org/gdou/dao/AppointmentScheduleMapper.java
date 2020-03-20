package org.gdou.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.gdou.model.po.AppointmentSchedule;
import org.gdou.model.po.example.AppointmentScheduleExample;

public interface AppointmentScheduleMapper {
    long countByExample(AppointmentScheduleExample example);

    int deleteByExample(AppointmentScheduleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppointmentSchedule record);

    int insertSelective(AppointmentSchedule record);

    List<AppointmentSchedule> selectByExample(AppointmentScheduleExample example);

    AppointmentSchedule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppointmentSchedule record, @Param("example") AppointmentScheduleExample example);

    int updateByExample(@Param("record") AppointmentSchedule record, @Param("example") AppointmentScheduleExample example);

    int updateByPrimaryKeySelective(AppointmentSchedule record);

    int updateByPrimaryKey(AppointmentSchedule record);
}
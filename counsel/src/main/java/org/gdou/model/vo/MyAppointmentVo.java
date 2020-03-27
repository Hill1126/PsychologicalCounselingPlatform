package org.gdou.model.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MyAppointmentVo {


    private Integer teacherId;

    private String teacherName;

    private LocalTime appointmentTime;

    private LocalDate appointmentDate;

    private Integer status;

    private Integer workOrderId;

}
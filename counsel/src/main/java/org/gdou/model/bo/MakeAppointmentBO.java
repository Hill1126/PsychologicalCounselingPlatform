package org.gdou.model.bo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/24
 **/
@Data
public class MakeAppointmentBO {

    private Integer studentId;
    private String studentName;
    private Integer teacherId;
    private String teacherName;
    private LocalDate  appointmentDate;
    private LocalTime appointmentTime;
}

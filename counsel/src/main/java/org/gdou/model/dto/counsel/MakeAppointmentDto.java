package org.gdou.model.dto.counsel;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/23
 **/
@Data
public class MakeAppointmentDto {

    private int studentId;
    private int teacherId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

}

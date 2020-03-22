package org.gdou.model.bo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/22
 **/
@Data
public class AppointmentTimeBo {

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

}

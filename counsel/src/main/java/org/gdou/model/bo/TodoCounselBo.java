package org.gdou.model.bo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/26
 **/
@Data
public class TodoCounselBo {

    private Integer studentId;
    private String studentName;
    private LocalTime appointmentTime;
    private LocalDate appointmentDate;
    private String title;

}

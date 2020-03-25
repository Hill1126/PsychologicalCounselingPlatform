package org.gdou.model.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/25
 **/
@Data
public class CounselHistoryVo {

    private Integer teacherId;
    private String teacherName;
    private Integer studentId;
    private String studentName;

    private String title;

    private LocalTime appointmentTime;

    private LocalDate appointmentDate;

    private Integer status;
}

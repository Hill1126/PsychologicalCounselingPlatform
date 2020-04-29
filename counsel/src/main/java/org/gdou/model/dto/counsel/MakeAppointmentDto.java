package org.gdou.model.dto.counsel;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/23
 **/
@Data
public class MakeAppointmentDto {

    @NotNull(message = "老师id不能为空")
    private int teacherId;
    @NotBlank(message = "老师姓名不能为空")
    private String teacherName;
    @Future(message = "预约时间已经无效")
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    @NotNull(message = "预约日期不能为空")
    private LocalDateTime appointmentDateTime;



}

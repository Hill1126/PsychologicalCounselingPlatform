package org.gdou.model.qo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/30
 **/
@Data
public class TimeQo {

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime time;
    private LocalDateTime dateTime;

}

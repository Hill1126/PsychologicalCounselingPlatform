package org.gdou.model.qo;

import lombok.Data;

import java.time.LocalDate;

/**
 * 存放需要查询的id，和时间间隔
 * @author HILL
 * @version V1.0
 * @date 2020/3/22
 **/
@Data
public class AvailableTimeQo {

    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;

    public AvailableTimeQo(Integer id, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }


}

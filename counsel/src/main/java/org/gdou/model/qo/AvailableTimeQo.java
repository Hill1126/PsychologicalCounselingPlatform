package org.gdou.model.qo;

import lombok.Data;

import java.time.LocalDate;

/**
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

package org.gdou.model.vo;

import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/22
 **/
@Getter
public class AppointmentTimeVo {

    private List<LocalTime> todayTimeList;
    private List<LocalTime> tomorrowTimeList;

    public AppointmentTimeVo(List<LocalTime> todayTimeList, List<LocalTime> tomorrowTimeList) {
        this.todayTimeList = todayTimeList;
        this.tomorrowTimeList = tomorrowTimeList;
    }
}

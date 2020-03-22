package org.gdou.model.vo;

import lombok.Getter;

import java.time.LocalTime;
import java.util.Map;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/22
 **/
@Getter
public class AppointmentTimeVo {

    private Map<LocalTime,Boolean> todayTimeMap;
    private Map<LocalTime,Boolean> tomorrowTimeMap;

    public AppointmentTimeVo(Map<LocalTime, Boolean> todayTimeMap, Map<LocalTime, Boolean> tomorrowTimeMap) {
        this.todayTimeMap = todayTimeMap;
        this.tomorrowTimeMap = tomorrowTimeMap;
    }
}

package org.gdou.common.constant.chat;

import java.time.LocalTime;
import java.util.HashMap;

/**
 * 描述时间段，用于预约时的时间段确定
 * @author HILL
 * @version V1.0
 * @date 2020/3/19
 **/
public class TimeQuantum {

    public static final LocalTime NINE_AM = LocalTime.of(9,0);
    public static final LocalTime TEN_AM = LocalTime.of(10,0);
    public static final LocalTime ELEVEN_AM = LocalTime.of(11,0);
    public static final LocalTime TWO_PM = LocalTime.of(14,0);
    public static final LocalTime THREE_PM = LocalTime.of(15,0);
    public static final LocalTime FOUR_PM = LocalTime.of(16,0);

    private static HashMap<LocalTime,Boolean> timeMap;

    static {
        timeMap = new HashMap(8);
        timeMap.put(NINE_AM,false);
        timeMap.put(TEN_AM,false);
        timeMap.put(ELEVEN_AM,false);
        timeMap.put(TWO_PM,false);
        timeMap.put(THREE_PM,false);
        timeMap.put(FOUR_PM,false);
    }

    /**
     * 把默认全部未预约的时间段map返回
     * @Author: HILL
     * @date: 2020/3/22 18:06
     *
     * @return: java.util.HashMap<java.time.LocalTime,java.lang.Boolean>
    **/
    public static HashMap<LocalTime,Boolean> getDefaultTimeMap(){
        var map = new HashMap<LocalTime,Boolean>(8);
        timeMap.putAll(map);
        return map;
    }


}

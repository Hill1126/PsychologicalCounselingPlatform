package org.gdou.common.constant.chat;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述时间段，用于预约时的时间段确定
 * @author HILL
 * @version V1.0
 * @date 2020/3/19
 **/
public class TimeQuantum {

    public static final int NINE_AM = 10;
    public static final int TEN_AM = 20;
    public static final int ELEVEN_AM = 30;
    public static final int TWO_PM = 40;
    public static final int THREE_PM = 50;
    public static final int FOUR_PM = 60;

    public static Map<Integer, LocalTime> timeMap;
    static{
         timeMap = new HashMap<>();
         timeMap.put(NINE_AM,LocalTime.of(9,0));
         timeMap.put(TEN_AM,LocalTime.of(10,0));
         timeMap.put(ELEVEN_AM,LocalTime.of(11,0));
         timeMap.put(TWO_PM,LocalTime.of(14,0));
         timeMap.put(THREE_PM,LocalTime.of(15,0));
         timeMap.put(FOUR_PM,LocalTime.of(16,0));
    }




}

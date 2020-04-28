package org.gdou.common.constant.chat;

import java.time.LocalTime;
import java.util.ArrayList;

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

    private static ArrayList<LocalTime> timeSet;

    static {
        timeSet = new ArrayList<>(8);
        timeSet.add(NINE_AM );
        timeSet.add(TEN_AM );
        timeSet.add(ELEVEN_AM );
        timeSet.add(TWO_PM );
        timeSet.add(THREE_PM );
        timeSet.add(FOUR_PM );
    }

    /**
     * 把默认全部未预约的时间段map返回
     * @Author: HILL
     * @date: 2020/3/22 18:06
     *
     * @return: java.util.HashMap<java.time.LocalTime,java.lang.Boolean>
    **/
    public static ArrayList<LocalTime> getDefaultTimeList(){
        var list = new ArrayList<LocalTime>();
        list.addAll(timeSet);
        return list;
    }


}

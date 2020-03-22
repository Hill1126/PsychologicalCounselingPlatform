package org.gdou.model.qo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 用于装载当前可以预约老师的query obj
 * @author HILL
 * @version V1.0
 * @date 2020/3/20
 **/
@Getter
@Setter
public class TeacherChatQo {

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isHot;


    public TeacherChatQo(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * 按照默认今天到明天的间隔新建一个qo
     * @Author: HILL
     * @date: 2020/3/20 21:18
     *
     * @return: org.gdou.model.qo.TeacherChatQo
    **/
    public static  TeacherChatQo quicklyBuild(){
        return new TeacherChatQo(LocalDate.now(),LocalDate.now().plusDays(1));
    }

}

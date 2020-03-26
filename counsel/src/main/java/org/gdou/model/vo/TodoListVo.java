package org.gdou.model.vo;

import lombok.Getter;

import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/26
 **/
@Getter
public class TodoListVo {
    private List todayCounsel;
    private List tomorrowCounsel;

    public TodoListVo(List todayCounsel, List tomorrowCounsel) {
        this.todayCounsel = todayCounsel;
        this.tomorrowCounsel = tomorrowCounsel;
    }
}

package org.gdou.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/24
 **/
@Getter
@Setter
public class TestRecordVo {


    private String paperTitle;

    private String paperAbstract;

    private LocalDateTime testTime;

    private String description;

    private Double totalScore;

}

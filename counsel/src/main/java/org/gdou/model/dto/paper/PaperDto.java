package org.gdou.model.dto.paper;

import lombok.Getter;
import lombok.Setter;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/22
 **/
@Getter
@Setter
public class PaperDto {

    private String paperTitle;

    private String paperType;

    private String paperAbstract;

    private Integer paperStatus;
}

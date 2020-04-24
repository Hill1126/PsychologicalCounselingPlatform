package org.gdou.model.dto.paper;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/22
 **/
@Getter
@Setter
public class PaperDto {

    @NotNull(message = "试卷id不能为空")
    private Integer id;

    private String paperTitle;

    private String paperType;

    private String paperAbstract;

    private Integer paperStatus;
}

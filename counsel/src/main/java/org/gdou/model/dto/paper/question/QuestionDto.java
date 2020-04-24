package org.gdou.model.dto.paper.question;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/23
 **/
@Getter
@Setter
public class QuestionDto {


    @NotNull(message = "试题id不能为空")
    private Integer questionId;

    private String questionTitle;

    private Integer questionStatus;
}

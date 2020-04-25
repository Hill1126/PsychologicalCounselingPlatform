package org.gdou.model.dto.paper.question;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/24
 **/
@Getter
@Setter
public class UpdateAnswerDto {

    @NotNull(message = "答案id不能为空")
    private Integer answerId;

    private String answerValue;

    private Double answerScore;

    private Integer answerStatus;
}

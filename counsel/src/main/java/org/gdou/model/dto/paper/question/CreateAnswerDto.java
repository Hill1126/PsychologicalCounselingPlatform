package org.gdou.model.dto.paper.question;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/24
 **/
@Getter
@Setter
public class CreateAnswerDto {

    @NotNull(message = "问题id不能为空")
    private Integer questionId;

    @NotBlank(message = "答案不能为空")
    private String answerValue;

    @NotNull(message = "答案分值不能为空")
    private Double answerScore;
}

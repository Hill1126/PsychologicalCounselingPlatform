package org.gdou.model.dto.counsel;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/18
 **/
@Data
public class WebSocketMessageDto {

    @NotBlank
    private int receiverId;
    @NotBlank
    private String receiverName;
    @NotBlank
    private String content;

}

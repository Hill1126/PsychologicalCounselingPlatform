package org.gdou.model.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/23
 **/
@Data
public class UserRegisterDto {
    @NotBlank(message = "名字不能为空")
    private String name;

    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

}

package org.gdou.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用于传输用户修改资料的对象
 * @author HILL
 * @version V1.0
 * @date 2020/3/7
 **/
@Data
public class UserInfoDto {

    @NotBlank(message = "用户id不能为空")
    private int id;
    private String name;
    private String phone;

}

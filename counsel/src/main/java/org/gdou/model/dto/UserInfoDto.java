package org.gdou.model.dto;

import lombok.Data;

/**
 * 用于传输用户修改资料的对象
 * @author HILL
 * @version V1.0
 * @date 2020/3/7
 **/
@Data
public class UserInfoDto {

    private String name;
    private String phone;

}

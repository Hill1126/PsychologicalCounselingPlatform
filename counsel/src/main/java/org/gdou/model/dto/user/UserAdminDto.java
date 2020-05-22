package org.gdou.model.dto.user;

import lombok.Getter;
import lombok.Setter;

/**
 * 用于限定管理员用户管理信息时能修改的数据
 * @author HILL
 * @version V1.0
 * @date 2020/5/19
 **/
@Getter
@Setter
public class UserAdminDto {


    private String name;

    private String email;

    private String avatarUrl;

    private String phone;
}

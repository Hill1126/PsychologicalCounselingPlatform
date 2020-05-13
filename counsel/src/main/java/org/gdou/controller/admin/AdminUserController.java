package org.gdou.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.annotaions.RoleControl;
import org.gdou.common.constant.user.UserType;
import org.gdou.service.impl.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/5/12
 **/
@RestController
@Slf4j
@Validated
@RoleControl(userType = UserType.ADMIN)
public class AdminUserController {

    UserService userService;




}

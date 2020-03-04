package org.gdou.controller;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.user.UserType;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultCode;
import org.gdou.model.po.Oauths;
import org.gdou.model.po.User;
import org.gdou.service.impl.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/2/6
 **/
@RestController
@Slf4j
@Validated
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 验证用户的登录信息，可以有账号、手机、邮箱三种形式
     * @Author: HILL
     * @date: 2020/3/4 22:16
     * @param oauth
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@Validated Oauths oauth, HttpSession session){
        Result result = userService.login(oauth);
        if (result.getCode()== ResultCode.SUCCESS){
            //将用户放入session会话中，返回结果
            var user =  (User)result.getData();
            session.setAttribute("user",user);

        }
        return result;
    }

    /**
     * 用户传入登录账号、姓名、密码即可完成注册
     * @Author: HILL
     * @date: 2020/3/2 17:37
     *
     * @param user
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result register(@Validated User user){
        user.setUserType(UserType.STUDENT);
        return userService.register(user);
    }

}

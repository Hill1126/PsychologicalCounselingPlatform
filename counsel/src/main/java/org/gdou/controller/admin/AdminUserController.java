package org.gdou.controller.admin;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.annotaions.RoleControl;
import org.gdou.common.constant.user.UserType;
import org.gdou.common.result.Result;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.dto.user.UserAdminDto;
import org.gdou.model.po.User;
import org.gdou.service.impl.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/5/12
 **/
@RestController
@Slf4j
@Validated
@RequestMapping("/admin/user")
@RoleControl(userType = UserType.ADMIN)
public class AdminUserController {

    UserService userService;

    /**
     * 根据用户类别返回相应的用户信息。
     * @Author: HILL
     * @date: 2020/5/19 13:24
     *
     * @param userType
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/list")
    public Result listUsers(PageInfoDto pageInfoDto,Integer userType){
        PageHelper.startPage(pageInfoDto.getPageNum(),pageInfoDto.getPageSize());
        return userService.listUsers(userType);
    }

    /**
     * 重置某个用户的密码
     * @Author: HILL
     * @date: 2020/5/19 13:39
     *
     * @param userId
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/reset")
    public Result reSetPassWord(@NotNull Integer userId){
        return userService.reSetPassWord(userId);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateUserInfo(@NotNull Integer userId, UserAdminDto userAdminDto){
        var user = new User();
        BeanUtils.copyProperties(userAdminDto,user);
        user.setId(userId);
        return Result.genSuccessResult(userService.updateUserInfo(user));

    }

    @RequestMapping("/delete")
    public Result deleteUser(@NotNull Integer userId){
        return  userService.deleteUser(userId);
    }


}

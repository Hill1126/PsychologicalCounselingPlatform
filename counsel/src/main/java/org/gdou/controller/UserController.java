package org.gdou.controller;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.constant.user.UserType;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultCode;
import org.gdou.common.result.ResultGenerator;
import org.gdou.model.dto.UserInfoDto;
import org.gdou.model.po.Oauths;
import org.gdou.model.po.User;
import org.gdou.service.impl.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/2/6
 **/
@RestController
@Slf4j
@Validated
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 验证用户的登录信息，可以有账号、邮箱、token三种形式
     * @Author: HILL
     * @date: 2020/3/7 22:26
     *
     * @param oauth 凭证和密码
     * @param session 存放用户的session
     * @param useTokenLogin 是否使用token免密码登录(暂不开发)
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(Oauths oauth, HttpSession session,boolean useTokenLogin){
        Result result = userService.login(oauth);
        if (result.getCode()== ResultCode.SUCCESS){
            //将用户放入session会话中，返回结果
            var user =  (User)result.getData();
            session.setAttribute(ProjectConstant.USER_SESSION_KEY,user);

        }
        return result;
    }

    /**
     * 用户传入登录账号、姓名、密码即可完成注册
     * @Author: HILL
     * @date: 2020/3/2 17:37
     * @param user 注册的信息
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result register(@Validated User user){
        user.setUserType(UserType.STUDENT);
        return userService.register(user);
    }

    @RequestMapping("/editInfo")
    public Result editInfo(UserInfoDto userInfo, MultipartFile avatar,HttpSession session)
            throws IOException {

        User user = new User();
        BeanUtils.copyProperties(userInfo,user);
        //判断是否有头像文件上传
        if (avatar !=null){
            //校验文件的后缀名是否为jpg、png
            var originalFileName = avatar.getOriginalFilename();
            var index = originalFileName.lastIndexOf(".");
            String suffixName = originalFileName.substring(index);
            if ( !(suffixName.toLowerCase()).contains("jpg") && !(suffixName.toLowerCase()).contains("png") ){
                return ResultGenerator.genFailResult("上传的文件必须为png、jpg文件");
            }

            //保存文件到项目的指定路径
            var fileName = UUID.randomUUID().toString()+suffixName;
            File file = new File("classpath:/image/fileName");
            avatar.transferTo(file);

        }
        user =  userService.updateUserInfo(user);
        session.setAttribute(ProjectConstant.USER_SESSION_KEY,user);
        return ResultGenerator.genSuccessResult(user);

    }

}

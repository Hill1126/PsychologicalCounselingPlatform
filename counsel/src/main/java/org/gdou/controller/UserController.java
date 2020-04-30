package org.gdou.controller;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.constant.user.UserType;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultCode;
import org.gdou.common.result.ResultGenerator;
import org.gdou.common.utils.CookieUtils;
import org.gdou.common.utils.RedisUtil;
import org.gdou.model.dto.user.UserInfoDto;
import org.gdou.model.dto.user.UserRegisterDto;
import org.gdou.model.po.Oauths;
import org.gdou.model.po.User;
import org.gdou.service.impl.BosService;
import org.gdou.service.impl.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
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
    private BosService bosService;
    private RedisUtil redisUtil;
    private ObjectMapper objectMapper;

    public UserController(UserService userService, BosService bosService, RedisUtil redisUtil, ObjectMapper objectMapper) {
        this.userService = userService;
        this.bosService = bosService;
        this.redisUtil = redisUtil;
        this.objectMapper = objectMapper;
    }

    /**
     * 验证用户的登录信息，可以有账号、邮箱、token三种形式
     * @Author: HILL
     * @date: 2020/3/7 22:26
     *
     * @param oauth 凭证和密码
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@Validated Oauths oauth,
                        HttpServletResponse response, HttpServletRequest request
                        ) throws JsonProcessingException {
        Result result = userService.login(oauth);
        if (result.getCode()== ResultCode.SUCCESS){
            ssoProcess(response, request, result);
        }
        return result;
    }

    private void ssoProcess(HttpServletResponse response, HttpServletRequest request, Result result) throws JsonProcessingException {
        //将用户redis中，并在cookie中添加token
        var user =  (User)result.getData();
        String token = UUID.randomUUID().toString();
        String userJson = objectMapper.writeValueAsString(user);
        //设置用户信息在redis的缓存
        redisUtil.setEx(ProjectConstant.USER_SESSION_KEY+token,
                userJson,ProjectConstant.USER_EXPIRE);
        //往用户写入token
        CookieUtils.setCookie(request,response,ProjectConstant.TOKEN_NAME,
                        token);
    }

    /**
     * 用户传入登录账号、姓名、密码即可完成注册
     * @Author: HILL
     * @date: 2020/3/2 17:37
     * @param userRegisterDto 注册的信息
     * @param authCode 根据此码认证可认证为教师
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result register(@Validated UserRegisterDto userRegisterDto, String authCode){
        var user = new User();
        BeanUtils.copyProperties(userRegisterDto,user);
        if(!StringUtils.isEmpty(authCode) && "Teacher".equals(authCode)){
            user.setUserType(UserType.TEACHER);
        }else{
            user.setUserType(UserType.STUDENT);
        }
        user.setCreatedAt(LocalDateTime.now());
        return userService.register(user);
    }

    @RequestMapping(value = "/editInfo",method = RequestMethod.POST)
    public Result editInfo(UserInfoDto userInfo, MultipartFile avatar,HttpSession session)
            throws IOException {
        var user = (User)session.getAttribute(ProjectConstant.USER_SESSION_KEY);
        BeanUtils.copyProperties(userInfo,user);
        //判断是否有头像文件上传
        if (avatar !=null){
            //保存文件到百度的文件管理库
            bosService.uploadAvatar(avatar,user);
        }
        user.setUpdatedAt(LocalDateTime.now());
        user =  userService.updateUserInfo(user);
        session.setAttribute(ProjectConstant.USER_SESSION_KEY,user);
        return ResultGenerator.genSuccessResult(user);

    }

    /**
     * 将session保存的用户信息删除并返回结果
     * @Author: HILL
     * @date: 2020/3/11 22:52
     *
     * @param session
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/exit")
    public Result exit(HttpSession session){
        session.removeAttribute(ProjectConstant.USER_SESSION_KEY);
        return ResultGenerator.genSuccessResult();
    }






}

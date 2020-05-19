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
import org.gdou.common.utils.UserUtils;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
        Cookie cookie = new Cookie(ProjectConstant.TOKEN_NAME,token);
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath("/");
        response.addCookie(cookie);
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
        }else if (!StringUtils.isEmpty(authCode) && "Admin".equals(authCode)){
            user.setUserType(UserType.ADMIN);
        }
        else{
            user.setUserType(UserType.STUDENT);
        }
        user.setCreatedAt(LocalDateTime.now());
        return userService.register(user);
    }

    @RequestMapping(value = "/editInfo",method = RequestMethod.POST)
    public Result editInfo(UserInfoDto userInfo, MultipartFile avatar,HttpServletRequest request)
            throws IOException {
        User user = UserUtils.getUserInRequest(request);
        BeanUtils.copyProperties(userInfo,user);
        //判断是否有头像文件上传
        if (avatar !=null){
            //保存文件到百度的文件管理库
            bosService.uploadAvatar(avatar,user);
        }
        user.setUpdatedAt(LocalDateTime.now());
        user =  userService.updateUserInfo(user);
        //更新在redis的user资料
        log.info("用户id【{}】，更新资料成功",user.getName());
        String token = CookieUtils.getCookieValue(request, ProjectConstant.TOKEN_NAME);
        String userJson = objectMapper.writeValueAsString(user);
        redisUtil.setEx(ProjectConstant.USER_SESSION_KEY+token,
                userJson,ProjectConstant.USER_EXPIRE);
        return ResultGenerator.genSuccessResult(user);

    }

    /**
     * 将保存在redis中的token信息删除，同时删除cookie
     * @Author: HILL
     * @date: 2020/4/30 16:06
     *
     * @param request
     * @param response
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/exit")
    public Result exit(HttpServletRequest request,HttpServletResponse response){
        String token = CookieUtils.getCookieValue(request, ProjectConstant.TOKEN_NAME);
        redisUtil.delete(ProjectConstant.USER_SESSION_KEY+token);
        CookieUtils.deleteCookie(request,response,ProjectConstant.TOKEN_NAME);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 根据token请求返回当前登录用户的数据
     * @Author: HILL
     * @date: 2020/5/5 15:57
     *
     * @param request
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/loginInfo")
    public Result getInfoByToken(HttpServletRequest request) throws JsonProcessingException {
        User user = UserUtils.getUserInRequest(request);
        return Result.genSuccessResult(user);
    }

    /**
     * 获取用户的头像
     * @Author: HILL
     * @date: 2020/5/19 15:05
     *
     * @param userId
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/avatar")
    public Result getAvatar(@NotNull Integer userId){
        return  userService.getAvatar(userId);
    }

    /**
     * 根据用户的旧密码验证更改为新的密码
     * @Author: HILL
     * @date: 2020/5/19 15:05
     *
     * @param oldPass
     * @param newPass
     * @return: org.gdou.common.result.Result
    **/
    @RequestMapping("/changePass")
    public Result updatePass(HttpServletRequest request,@NotBlank String oldPass,@NotBlank String newPass){
        User user = UserUtils.getUserInRequest(request);
        return userService.updatePassWord(user,oldPass,newPass);
    }



}

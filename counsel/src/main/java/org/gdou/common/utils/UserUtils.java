package org.gdou.common.utils;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.model.po.User;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/22
 **/
@Component
public class UserUtils {


    private  static RedisUtil redisUtil;

    private static ObjectMapper objectMapper;

    @Autowired
    public UserUtils(RedisUtil redisUtil,ObjectMapper objectMapper){
        UserUtils.redisUtil = redisUtil;
        UserUtils.objectMapper = objectMapper;
    }

    /**
     * 根据cookie里面的token，从redis中返回用户的登录信息
     * @Author: HILL
     * @date: 2020/4/30 11:44
     *
     * @param request http请求，从中获取cookie
     * @return: org.gdou.model.po.User
    **/
    public static User getUserByToken(HttpServletRequest request) throws JsonProcessingException {

        //通过request获取token
        String token = CookieUtils.getCookieValue(request, ProjectConstant.TOKEN_NAME);
        return getUserByToken(token);

    }

    @Nullable
    public static User getUserByToken(String token) throws JsonProcessingException {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        //从redis获取用户信息
        String userJson = redisUtil.get(ProjectConstant.USER_SESSION_KEY + token);
        //判断token是否正确，userJson为null则为失效
        if (StringUtils.isEmpty(userJson)){
            return null;
        }else{
            User user = objectMapper.readValue(userJson, User.class);
            redisUtil.expire(ProjectConstant.USER_SESSION_KEY+token,ProjectConstant.USER_EXPIRE);
            return user;
        }
    }

    public static User getUserInRequest( HttpServletRequest request) {
        return (User)request.getSession().getAttribute(ProjectConstant.USER_SESSION_KEY);
    }



}

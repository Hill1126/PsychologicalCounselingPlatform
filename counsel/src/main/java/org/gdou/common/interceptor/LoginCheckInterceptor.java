package org.gdou.common.interceptor;

import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.result.ResultGenerator;
import org.gdou.model.po.Oauths;
import org.gdou.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录检查过滤器
 * @author HILL
 * @version V1.0
 * @date 2020/3/11
 **/
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Value("${spring.profiles.active}")
    private String active;
    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var user = request.getSession().getAttribute(ProjectConstant.USER_SESSION_KEY);
        if (user == null && "dev".equals(active)){
            userService.login(Oauths.builder().oauthId("testStu").credential("123456").build());
            return true;
        }
        if (user==null){
            response.setContentType("text/json;charset=UTF-8");
            response.getWriter().write(ResultGenerator.genFailResult("请登陆后操作").toString());
            return false;
        }
        return true;
    }



}

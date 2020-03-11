package org.gdou.common.interceptor;

import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.result.ResultGenerator;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var user = request.getSession().getAttribute(ProjectConstant.USER_SESSION_KEY);
        if (user==null){
            response.setContentType("text/json;charset=UTF-8");
            response.getWriter().write(ResultGenerator.genFailResult("请登陆后操作").toString());
            return false;
        }
        return true;
    }



}

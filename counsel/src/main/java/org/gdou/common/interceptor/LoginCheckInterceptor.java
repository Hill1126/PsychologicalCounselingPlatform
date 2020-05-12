package org.gdou.common.interceptor;

import org.gdou.common.annotaions.RoleControl;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultCode;
import org.gdou.common.utils.UserUtils;
import org.springframework.web.method.HandlerMethod;
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
        var user = UserUtils.getUserByToken(request);
        if (user==null){
            response.setContentType("text/json;charset=UTF-8");
            Result result = new Result();
            result.setMessage("请登陆后操作");
            result.setCode(ResultCode.UNAUTHORIZED);
            response.getWriter().write(result.toString());
            return false;
        }
        //检查注解是否需要权限校验。
        // 这个方法一般就是controller
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //类上的注解
        RoleControl typeRole = ((HandlerMethod) handler).getBeanType().getAnnotation(RoleControl.class);
        //方法上的注解
        RoleControl methodRole = ((HandlerMethod) handler).getMethod().getAnnotation(RoleControl.class);
        var roleControl = methodRole==null?typeRole:methodRole;
        //通过注解判断权限控制
        if (roleControl!=null){
            int requireType = roleControl.userType();
            Integer userType = user.getUserType();
            //值越小权限越大，若当前用户大于需求权限，则放行
            if (userType.compareTo(requireType)>0){
                response.setContentType("text/json;charset=UTF-8");
                Result result = new Result();
                result.setMessage("无权限操作");
                result.setCode(ResultCode.UNAUTHORIZED);
                response.getWriter().write(result.toString());
                return false;
            }
        }

        //为每一个请求带上user
        request.setAttribute(ProjectConstant.USER_SESSION_KEY,user);
        return true;
    }



}

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
import java.io.IOException;
import java.util.Arrays;

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
            writeUnauthorizedResult(response, "请登陆后操作");
            return false;
        }
        //检查注解是否需要权限校验。
        // 这个方法一般就是controller
        var realHandler = handler;
        if (handler instanceof HandlerMethod){
            realHandler = (HandlerMethod) handler;
            //类上的注解
            RoleControl typeRole = ((HandlerMethod) handler).getBeanType().getAnnotation(RoleControl.class);
            //方法上的注解
            RoleControl methodRole = ((HandlerMethod) handler).getMethod().getAnnotation(RoleControl.class);
            var roleControl = methodRole==null?typeRole:methodRole;
            //通过注解判断权限控制
            if (roleControl!=null){
                //检测注解是否含有当前用户所代表的角色
                Integer userType = user.getUserType();
                boolean hasRole = Arrays.stream(roleControl.userType()).anyMatch((roleCode) -> userType.equals(roleCode));
                //如果没有权限
                if (!hasRole){
                    writeUnauthorizedResult(response, "无权限操作");
                    return false;
                }
            }
        }


        //为每一个请求带上user
        request.setAttribute(ProjectConstant.USER_SESSION_KEY,user);
        return true;
    }

    private void writeUnauthorizedResult(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("text/json;charset=UTF-8");
        Result result = new Result();
        result.setMessage(msg);
        result.setCode(ResultCode.UNAUTHORIZED);
        response.getWriter().write(result.toString());
    }


}

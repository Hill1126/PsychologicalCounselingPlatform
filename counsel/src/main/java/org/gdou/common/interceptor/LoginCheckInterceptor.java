package org.gdou.common.interceptor;

import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultCode;
import org.gdou.common.utils.UserUtils;
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
        var user = UserUtils.getUserInRequest(request);
        if (user == null && "dev".equals(active)){
            Result result = userService.login(Oauths.builder().oauthId("testStu").credential("123456").build());
            request.getSession().setAttribute(ProjectConstant.USER_SESSION_KEY,result.getData());
            return true;
        }
        if (user==null){
            response.setContentType("text/json;charset=UTF-8");
            Result result = new Result();
            result.setMessage("请登陆后操作");
            result.setCode(ResultCode.UNAUTHORIZED);
            response.getWriter().write(result.toString());
            return false;
        }
        return true;
    }



}

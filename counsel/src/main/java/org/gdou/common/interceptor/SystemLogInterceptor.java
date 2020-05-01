package org.gdou.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于记录访问的日志拦截器
 * @author HILL
 * @version V1.0
 * @date 2020/3/5
 **/
@Slf4j
public class SystemLogInterceptor implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.getSession(true);
        return true;
    }


}

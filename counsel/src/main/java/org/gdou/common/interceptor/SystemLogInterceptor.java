package org.gdou.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String remoteAddr = request.getRemoteAddr();
        String uri = request.getRequestURI();
        String refer = request.getHeader("Referer");
        log.info("ip:{} 从{}转入  访问了接口：{} ",remoteAddr,refer,uri);

    }
}

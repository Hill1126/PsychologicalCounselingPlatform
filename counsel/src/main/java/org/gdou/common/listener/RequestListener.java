package org.gdou.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.utils.UserUtils;
import org.gdou.model.po.User;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * 使所有请求携带httpSession
 * @author HILL
 * @version V1.0
 * @date 2020/3/16
 **/
@WebListener
@Slf4j
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre)  {
        var request = ((HttpServletRequest) sre.getServletRequest());
        //将所有request请求都携带上httpSession
        var session =request.getSession(true);
        //防止用户session没有放入user，在这里尝试放入
        User user = UserUtils.getUserInRequest(request);
        session.setAttribute(ProjectConstant.USER_SESSION_KEY,user);
        log.info("将所有request请求都携带上httpSession {}",session.getId());

    }




}

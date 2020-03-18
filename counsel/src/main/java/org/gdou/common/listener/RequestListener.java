package org.gdou.common.listener;

import lombok.extern.slf4j.Slf4j;

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
        //将所有request请求都携带上httpSession
        var session = ((HttpServletRequest) sre.getServletRequest()).getSession();
        log.info("将所有request请求都携带上httpSession {}",session.getId());

    }


}

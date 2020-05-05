package org.gdou.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.utils.CookieUtils;
import org.gdou.common.utils.UserUtils;
import org.gdou.model.po.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/16
 **/
@Configuration
@Slf4j
public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        log.info("接受websocket连接请求:URL为【{}】,",request.getRequestURI());
        Map<String, List<String>> header = request.getHeaders();
        List<String> cookie = header.get("cookie");
        //获取存放于header的cookie，通过token获取user
        if (cookie!=null && cookie.size()>0){
            String cookieStr = cookie.get(0);
            log.info("当前cookie字符串：【{}】",cookieStr);
            String token = CookieUtils.getCookieInHeaderString(ProjectConstant.TOKEN_NAME, cookieStr);
            log.info("websocket获取到用户token：【{}】",token);
            try {
                //根据token获取用户，并传入websocket
                User user = UserUtils.getUserByToken(token);
                if (user!=null){
                    sec.getUserProperties().put(ProjectConstant.USER_SESSION_KEY, user);
                }
            } catch (JsonProcessingException e) {
                log.error("用户信息json转换失败");
            }

        }else {
            log.info("webSocket获取cookie失败，cookie数量为0");
        }
        super.modifyHandshake(sec,request,response);
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }



}

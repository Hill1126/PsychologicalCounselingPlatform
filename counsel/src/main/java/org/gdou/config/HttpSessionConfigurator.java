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
        Map<String, List<String>> header = request.getHeaders();
        List<String> cookie = header.get("cookie");
        //获取存放于header的cookie，通过token获取user
        if (cookie!=null && cookie.size()>0){
            String cookieStr = cookie.get(0);
            String token = CookieUtils.getCookieInHeaderString(ProjectConstant.TOKEN_NAME, cookieStr);
            try {
                //根据token获取用户，并传入websocket
                User user = UserUtils.getUserByToken(token);
                sec.getUserProperties().put(ProjectConstant.USER_SESSION_KEY, user);
            } catch (JsonProcessingException e) {
                log.error("用户信息json转换失败");
            }

        }
        super.modifyHandshake(sec,request,response);
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }



}

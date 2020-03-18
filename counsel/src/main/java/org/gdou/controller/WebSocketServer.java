package org.gdou.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.exception.WebSocketNullPointException;
import org.gdou.config.HttpSessionConfigurator;
import org.gdou.model.dto.WebSocketMessageDto;
import org.gdou.model.po.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/15
 **/
@Component
@Slf4j
@ServerEndpoint(value = "/chat/{orderId}" ,configurator = HttpSessionConfigurator.class)
public class WebSocketServer {

    /**
     * 存放当前连接的session，根据用户的id来确定发送的对象
    **/
    private static Map<Integer, WebSocketServer> onlineClient = new ConcurrentHashMap<>();

    private User user ;

    private int orderId;

    private static ObjectMapper objectMapper  = new ObjectMapper();
    private Session session;


    /**
     * 当客户端打开连接：1.添加会话对象 2.更新在线人数
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config , @PathParam("orderId") int orderId) throws IOException {
        this.user = getUser(config);
        int currentUserId = user.getId();
        log.info("用户id{} 已连接",currentUserId);
        this.orderId = orderId;
        //放入map中，此session是javax.websocket.session
        this.session = session;
        if (onlineClient.containsKey(currentUserId)){
            session.close(new CloseReason(CloseReason.CloseCodes.TRY_AGAIN_LATER,"您一在另一客户端连接！"));
        }
        onlineClient.put(currentUserId,this);

    }

    @NotNull
    private User getUser(EndpointConfig config) {
        //获取user信息
        var httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
        if (httpSession==null){
            throw new WebSocketNullPointException("用户会话session不存在");
        }
        var user = (User)httpSession.getAttribute(ProjectConstant.USER_SESSION_KEY);
        if (user==null) {
            throw new WebSocketNullPointException("用户未登录");
        }
        return user;
    }

    /**
     * 根据目标id，将用户发送的消息推送，并记录聊天记录。
     * @Author: HILL
     * @date: 2020/3/15 23:24
     *
     * @param session 当前连接的session
     * @param messageObj 以json格式接收消息描述
     * @return: void
    **/
    @OnMessage
    public void onMessage(Session session, String messageObj) throws IOException {
        var messageDto = objectMapper.readValue(messageObj, WebSocketMessageDto.class);
        boolean success = sendMessage(messageDto);
        //如果成功，则记录消息
        if (success){

        }

    }

    /**
     * 当关闭连接：1.移除会话对象
     */
    @OnClose
    public void onClose(Session session) {
        int currentUserId = user.getId();
        log.info("用户id{} 已断开",currentUserId);
        onlineClient.remove(currentUserId);
    }

    /**
     * 当通信发生异常：打印错误日志
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(error.getMessage());
    }

    /**
     * 根据dto里面的id发送消息
     * @Author: HILL
     * @date: 2020/3/18 16:55
     *
     * @param messageDto 消息dto
     * @return: boolean 发送成功返回true，失败返回false
    **/
    private static boolean sendMessage(@Validated WebSocketMessageDto messageDto) throws IOException {
        int receiverId = messageDto.getReceiverId();
        if (!onlineClient.containsKey(receiverId)) {
            return false;
        }
        WebSocketServer socketServer = onlineClient.get(receiverId);
        socketServer.session.getBasicRemote().sendText(messageDto.getContent());

        return true;

    }

}

package org.gdou.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.exception.WebSocketNullPointException;
import org.gdou.config.HttpSessionConfigurator;
import org.gdou.dao.MsgRecordMapper;
import org.gdou.dao.WorkOrderMapper;
import org.gdou.model.dto.WebSocketMessageDto;
import org.gdou.model.po.MsgRecord;
import org.gdou.model.po.User;
import org.gdou.model.po.example.WorkOrderExample;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
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
    private Session session;


    private static ObjectMapper objectMapper  = new ObjectMapper();
    /**
     * 由于ServerEndPoint不是spring管理，这里提供注入方法给spring注入。
     **/
    private static WorkOrderMapper workOrderMapper;
    private static MsgRecordMapper msgRecordMapper;

    @Autowired
    public void setWorkOrderMapper(WorkOrderMapper workOrderMapper) {
        WebSocketServer.workOrderMapper = workOrderMapper;
    }

    @Autowired
    public void setMsgRecordMapper(MsgRecordMapper msgRecordMapper) {
        WebSocketServer.msgRecordMapper = msgRecordMapper;
    }

    /**
     * 当客户端打开连接：1.添加会话对象 2.更新在线人数
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config , @PathParam("orderId") int orderId) throws IOException {
        this.user = getUser(config);
        int currentUserId = user.getId();
        this.orderId = orderId;
        //放入map中，此session是javax.websocket.session
        this.session = session;
        //验证连接条件,验证当前工单是否存在或已完结。
        WorkOrderExample workOrderExample = new WorkOrderExample();
        var criteria = workOrderExample.createCriteria();
        criteria.andIdEqualTo(orderId).andStatusEqualTo(1);
        boolean exitsOrder = workOrderMapper.countByExample(workOrderExample)>0?true:false;
        //如果已存在连接，或工单状态检查失败
        if (onlineClient.containsKey(currentUserId) && !exitsOrder){
            onClose(session);
            log.info("用户{} 连接验证失败",user.getName());
        }
        onlineClient.put(currentUserId,this);
        log.info("用户id{} 已连接",currentUserId);

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
            MsgRecord msgRecord = MsgRecord.builder().orderId(this.orderId).content(messageDto.getContent())
                    .senderName(user.getName()).receiverName(messageDto.getReceiverName())
                    .time(LocalDateTime.now()).build();
            msgRecordMapper.insert(msgRecord);
            //更新redis的记录时间
        }else {
            session.getBasicRemote().sendText("【系统消息】消息发送失败，对方已断开连接！");
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

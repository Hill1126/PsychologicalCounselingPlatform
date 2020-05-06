package org.gdou.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.constant.chat.WorkOrderStatus;
import org.gdou.common.constant.user.UserType;
import org.gdou.common.exception.runtime.WebSocketNullPointException;
import org.gdou.common.utils.RedisUtil;
import org.gdou.config.HttpSessionConfigurator;
import org.gdou.dao.WorkOrderMapper;
import org.gdou.model.po.MsgRecord;
import org.gdou.model.po.User;
import org.gdou.model.po.WorkOrder;
import org.gdou.service.impl.CounselService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@ServerEndpoint(value = "/chat/{workOrderId}" ,configurator = HttpSessionConfigurator.class)
public class WebSocketServer {

    /**
     * 存放当前连接的session，根据用户的id来确定发送的对象
    **/
    private static Map<Integer, WebSocketServer> onlineClient = new ConcurrentHashMap<>();

    @Getter
    private User user ;
    private int workOrderId;
    private Session session;
    private Integer targetUserId;

    private static ObjectMapper objectMapper  = new ObjectMapper();
    /**
     * 由于ServerEndPoint不是spring管理，这里提供注入方法给spring注入。
     **/
    private static WorkOrderMapper workOrderMapper;
    private static CounselService counselService;
    private static RedisUtil redisUtil;

    @Autowired
    public  void setRedisUtil(RedisUtil redisUtil) {
        WebSocketServer.redisUtil = redisUtil;
    }

    @Autowired
    public void setWorkOrderMapper(WorkOrderMapper workOrderMapper) {
        WebSocketServer.workOrderMapper = workOrderMapper;
    }

    @Autowired
    public  void setCounselService(CounselService counselService) {
        WebSocketServer.counselService = counselService;
    }


    /**
     * 当客户端打开连接：1.添加会话对象 2.更新在线人数
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config , @PathParam("workOrderId") int workOrderId) throws IOException {
        this.user = getUserFromConfig(config);
        int currentUserId = user.getId();
        this.workOrderId = workOrderId;
        //放入map中，此session是javax.websocket.session
        this.session = session;
        //验证连接条件,验证当前工单是否存在或已完结。
        WorkOrder order = counselService.getByOrderId(this.workOrderId, WorkOrderStatus.READY);
        boolean exitsOrder = false;
        //如果存在此工单，设置当前用户id和目标id
        if (order!=null ){
            //设置目标id，并且保证当前连接用户时工单中的预约用户
            if (user.getUserType().equals(UserType.STUDENT)){
                exitsOrder = this.user.getId().equals(order.getStudentId());
                this.targetUserId = order.getTeacherId();
                log.info("当前用户是学生用户，id为【{}】，目标老师用户id为【{}】，该订单是否属于用户【{}】："
                            ,this.user.getId(),this.targetUserId,exitsOrder);
            }else if (user.getUserType().equals(UserType.TEACHER)){
                exitsOrder = user.getId().equals(order.getTeacherId());
                this.targetUserId = order.getStudentId();
                log.info("当前用户是老师用户，id为【{}】，目标学生用户id为【{}】，该订单是否属于用户【{}】："
                        ,this.user.getId(),this.targetUserId,exitsOrder);
            }
        }
        //如果已存在连接，或工单状态检查失败
        boolean exitsSession = onlineClient.containsKey(currentUserId);
        if (exitsSession || !exitsOrder){
            log.info("用户{} 连接验证失败,工单验证为【{}】，连接集合是否冲突【{}】",user.getName()
                        ,exitsOrder,exitsSession);
            this.session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION,"请传入预约工单号"));
        }
        onlineClient.put(currentUserId,this);
        log.info("用户id{} 已连接",currentUserId);

    }

    @NotNull
    private User getUserFromConfig(EndpointConfig config) {
        //获取user信息
        var user = (User)config.getUserProperties().get(ProjectConstant.USER_SESSION_KEY);
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
     * @param msg 字符串信息
     * @return: void
    **/
    @OnMessage
    public void onMessage(Session session, String msg) throws IOException {
        log.info("准备发送消息");
        boolean success = sendMessage(msg);
        //如果成功，则记录消息
        if (success){
            buildMsgRecord(msg);
        }else {
            session.getBasicRemote().sendText("【系统消息】消息发送失败，对方已断开连接！");
        }

    }

    private void buildMsgRecord(String msg) {
        MsgRecord msgRecord = MsgRecord.builder().orderId(this.workOrderId).content(msg)
                .senderName(user.getName()).receiverName(onlineClient.get(this.targetUserId).getUser().getName())
                .time(LocalDateTime.now()).build();
        counselService.insertMsgRecord(msgRecord);
    }


    /**
     * 当关闭连接：1.移除会话对象
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        int currentUserId = user.getId();
        log.info("用户id{} 已断开",currentUserId);
        onlineClient.remove(currentUserId);
        session.close();
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
     * @param msg 消息详情
     * @return: boolean 发送成功返回true，失败返回false
    **/
    private  boolean sendMessage( String msg) throws IOException {
        int receiverId = this.targetUserId;
        if (!onlineClient.containsKey(receiverId)) {
            return false;
        }
        WebSocketServer socketServer = onlineClient.get(receiverId);
        if(socketServer.session.isOpen()){
            socketServer.session.getBasicRemote().sendText(msg);
            log.info("消息发送成功{}",msg);
            return true;
        }else {
            return false;
        }


    }

}

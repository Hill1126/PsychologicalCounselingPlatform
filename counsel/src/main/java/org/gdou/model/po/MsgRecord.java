package org.gdou.model.po;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MsgRecord {
    private Integer id;

    private Integer orderId;

    private String senderName;

    private String receiverName;

    private LocalDateTime time;

    private String content;

    public MsgRecord(){

    }

    public MsgRecord(Integer id, Integer orderId, String senderName, String receiverName, LocalDateTime time, String content) {
        this.id = id;
        this.orderId = orderId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.time = time;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName == null ? null : senderName.trim();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}
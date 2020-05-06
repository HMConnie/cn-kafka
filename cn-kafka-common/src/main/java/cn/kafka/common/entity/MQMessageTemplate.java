package cn.kafka.common.entity;

import com.sgcai.commons.lang.base.BasicTO;

import java.util.Date;


public class MQMessageTemplate extends BasicTO {

    private static final long serialVersionUID=1L;

    /**
     * 事件NO
     */
    private String id;

    /**
     * ACK NO
     */
    private String ackId;

    /**
     * 事件类型
     */
    private MQMessageType messageType;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 消息体
     */
    private Object data;
    
    /**
     * 自定义参数
     */
    private String ackTopic;
    
    public String getAckTopic() {
        return ackTopic;
    }
    
    public void setAckTopic(String ackTopic) {
        this.ackTopic=ackTopic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getAckId() {
        return ackId;
    }

    public void setAckId(String ackId) {
        this.ackId=ackId;
    }

    public MQMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MQMessageType messageType) {
        this.messageType=messageType;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime=sendTime;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data=data;
    }
}

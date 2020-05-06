package cn.kafka.common.entity;

import com.sgcai.commons.lang.base.BasicTO;

import java.util.Date;


public class MQMessage extends BasicTO {

    private static final long serialVersionUID=1L;

    private String id;

    private String topic;

    private String ackId;

    private MQMessageType messageType;

    private MQMessageStatus status;

    private String content;

    private String ackContent;

    private Long sendCnt;

    private Date nextSendTime;

    private Date receiveTime;

    private Date lastSendTime;

    private Date createTime;

    private String ackTopic;

    public String getAckTopic() {
        return ackTopic;
    }

    public void setAckTopic(String ackTopic) {
        this.ackTopic=ackTopic;
    }

    public MQMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MQMessageType messageType) {
        this.messageType=messageType;
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

    public MQMessageStatus getStatus() {
        return status;
    }

    public void setStatus(MQMessageStatus status) {
        this.status=status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content=content;
    }

    public String getAckContent() {
        return ackContent;
    }

    public void setAckContent(String ackContent) {
        this.ackContent=ackContent;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime=receiveTime;
    }

    public Date getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime=lastSendTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime=createTime;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic=topic;
    }

    public Long getSendCnt() {
        return sendCnt;
    }

    public void setSendCnt(Long sendCnt) {
        this.sendCnt=sendCnt;
    }

    public Date getNextSendTime() {
        return nextSendTime;
    }

    public void setNextSendTime(Date nextSendTime) {
        this.nextSendTime=nextSendTime;
    }

}

package cn.kafka.common.dao;

import java.util.Date;
import java.util.List;

import cn.kafka.common.entity.MQMessage;
import cn.kafka.common.entity.MQMessageStatus;
import cn.kafka.common.entity.MQMessageType;
import org.apache.ibatis.annotations.Param;


public interface MQMessageDAO {

    MQMessage selectOne(@Param("id") String id, @Param("messageType") MQMessageType messageType);

    void setAck(@Param("id") String id, @Param("messageType") MQMessageType messageType, @Param("ackId") String ackId,
                @Param("ackContent") String ackContent, @Param("status") MQMessageStatus status);

    void insert(MQMessage message);

    void updateMessage(@Param("id") String id, @Param("messageType") MQMessageType messageType,
                       @Param("nextSendTime") Date nextSendTime, @Param("lastSendTime") Date lastSendTime);

    List<MQMessage> selectNeedSendMessage();
}

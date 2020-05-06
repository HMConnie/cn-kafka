package cn.kafka.provider.service;

import cn.kafka.common.dao.MQMessageDAO;
import cn.kafka.common.entity.MQMessage;
import cn.kafka.common.entity.MQMessageStatus;
import cn.kafka.common.entity.MQMessageTemplate;
import cn.kafka.common.entity.MQMessageType;
import cn.kafka.common.mq.ConsumerMessageMQ;
import cn.kafka.common.utils.JSONUtils;
import cn.kafka.common.utils.TopicConstant;
import cn.kafka.lib.ProducerManager;
import com.alibaba.fastjson.JSON;
import com.sgcai.commons.lang.utils.Dui1DuiStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("sendMQService")
@Transactional(rollbackFor = {Throwable.class})
public class SendMQService {

    @Autowired
    private ProducerManager producerManager;

    @Autowired
    private MQMessageDAO messageDAO;

    public void sendMQ(ConsumerMessageMQ consumerMessageMQ) {

        //发送消息需要应答,则发送之前必须先入库后发送mq
        MQMessage message = new MQMessage();
        message.setTopic(TopicConstant.CONSUMER_TOPIC);
        message.setId(Dui1DuiStringUtils.generateUUID());
        message.setContent(JSONUtils.convertString(consumerMessageMQ));
        message.setLastSendTime(new Date());
        message.setStatus(MQMessageStatus.WAIT_ACK);
        message.setMessageType(MQMessageType.SEND);
        message.setSendCnt(0L);
        message.setNextSendTime(new Date());
        message.setAckTopic(TopicConstant.CUSTOM_ACK_TOPIC);
        messageDAO.insert(message);


        //执行发送
        MQMessageTemplate template = new MQMessageTemplate();
        template.setId(message.getId());
        template.setSendTime(message.getLastSendTime());
        template.setAckTopic(message.getAckTopic());
        template.setMessageType(message.getMessageType());
        template.setData(consumerMessageMQ);
        producerManager.sendMessage(message.getTopic(), JSON.toJSONString(template));

    }

}

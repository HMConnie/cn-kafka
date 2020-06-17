package cn.kafka.common.consumer;

import cn.kafka.common.dao.MQMessageDAO;
import cn.kafka.common.entity.MQMessage;
import cn.kafka.common.entity.MQMessageStatus;
import cn.kafka.common.entity.MQMessageTemplate;
import cn.kafka.common.entity.MQMessageType;
import cn.kafka.common.lock.DistributedLock;
import cn.kafka.common.utils.JSONUtils;
import cn.kafka.lib.Consumer;
import cn.kafka.lib.ProducerManager;
import com.alibaba.fastjson.JSON;
import com.sgcai.commons.lang.utils.Dui1DuiStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public abstract class AbstractConsumer<T> extends Consumer {

    private static final Logger LOGGER=LoggerFactory.getLogger(AbstractConsumer.class);

    @Autowired
    private MQMessageDAO mqMessageDAO;

    @Autowired
    private ProducerManager producerManager;

    protected void process(String msg) throws Exception {

        MQMessageTemplate template=JSON.parseObject(msg, MQMessageTemplate.class);
        if (!MQMessageType.SEND.equals(template.getMessageType())) {
            return;
        }

        boolean isLock=false;

        try {
            isLock=getDistributedLock().getLock(this.getClass().getName() + "-" + template.getId() + "-" + MQMessageType.SEND);

            if (!isLock) {
                return;
            }

            MQMessage message=mqMessageDAO.selectOne(template.getId(), MQMessageType.ACK);
            if (message != null && message.getStatus().equals(MQMessageStatus.ACKED)) {
                ack(JSONUtils.parseObject(message.getAckContent()), message.getAckId(), message.getId(), template.getAckTopic());
                return;
            }

            Object ackData=execute(JSONUtils.convert(template.getData(), getMessageClass()));
            String ackId= Dui1DuiStringUtils.generateUUID();

            message=new MQMessage();
            message.setId(template.getId());
            message.setAckId(ackId);
            message.setTopic(getTopic());
            message.setContent(JSONUtils.convertString(template.getData()));
            message.setAckContent(JSONUtils.convertString(ackData));
            message.setStatus(MQMessageStatus.ACKED);
            message.setReceiveTime(new Date());
            message.setSendCnt(0L);
            message.setLastSendTime(template.getSendTime());
            message.setNextSendTime(template.getSendTime());
            message.setMessageType(MQMessageType.ACK);

            // 消费者存储生产者传递的参数
            message.setAckTopic(template.getAckTopic());

            mqMessageDAO.insert(message);

            ack(ackData, ackId, template.getId(), template.getAckTopic());
        } finally {
            if (isLock) {
                getDistributedLock().releaseLock();
            }
        }
    }

    private void ack(Object ackData, String ackId, String id, String ackTopic) {
        try {
            MQMessageTemplate template=new MQMessageTemplate();
            template.setAckId(ackId);
            template.setData(ackData);
            template.setId(id);
            template.setMessageType(MQMessageType.ACK);
            template.setSendTime(new Date());
            producerManager.sendMessage(ackTopic, JSON.toJSONString(template));
        } catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    protected abstract Class<T> getMessageClass();

    protected abstract Object execute(T data) throws Exception;

    /**
     * 获取锁
     * @return
     */
    protected abstract DistributedLock getDistributedLock();
}

package cn.kafka.consumer.mq;

import cn.kafka.common.consumer.AbstractConsumer;
import cn.kafka.common.lock.DistributedLock;
import cn.kafka.common.mq.ConsumerMessageMQ;
import cn.kafka.common.mq.CustomMQ;
import cn.kafka.common.utils.TopicConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("messageConsumer")
public class MessageConsumer extends AbstractConsumer<ConsumerMessageMQ> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    private DistributedLock distributedLock;

    @Override
    protected DistributedLock getDistributedLock() {
        return distributedLock;
    }

    @Override
    protected Class<ConsumerMessageMQ> getMessageClass() {
        return ConsumerMessageMQ.class;
    }

    @Override
    protected Object execute(ConsumerMessageMQ data) throws Exception {
        LOGGER.info("MessageConsumer receiver = " + data.toString());
        CustomMQ customMQ = new CustomMQ();
        customMQ.setContent("ack  = " + data.getContent());
        return customMQ;
    }

    @Override
    protected String getTopic() {
        return TopicConstant.CONSUMER_TOPIC;
    }

    @Override
    protected String getGroupId() {
        return TopicConstant.CONSUMER_GROUP;
    }
}

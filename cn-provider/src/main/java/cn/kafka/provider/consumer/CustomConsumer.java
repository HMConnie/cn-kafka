package cn.kafka.provider.consumer;

import cn.kafka.common.consumer.AbstractACKConsumer;
import cn.kafka.common.lock.DistributedLock;
import cn.kafka.common.mq.CustomMQ;
import cn.kafka.common.utils.TopicConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("customConsumer")
public class CustomConsumer extends AbstractACKConsumer<CustomMQ> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomConsumer.class);

    @Autowired
    private DistributedLock distributedLock;

    @Override
    protected String getTopic() {
        return TopicConstant.CUSTOM_ACK_TOPIC;
    }

    @Override
    protected Class<CustomMQ> getMessageClass() {
        return CustomMQ.class;
    }

    @Override
    protected DistributedLock getDistributedLock() {
        return distributedLock;
    }

    @Override
    protected void execute(CustomMQ data) throws Exception {
        LOGGER.info("CustomConsumer ack message:" + data.toString());
    }

}

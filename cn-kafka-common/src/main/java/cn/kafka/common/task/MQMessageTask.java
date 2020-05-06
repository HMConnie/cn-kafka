package cn.kafka.common.task;

import cn.kafka.common.dao.MQMessageDAO;
import cn.kafka.common.entity.MQMessage;
import cn.kafka.common.entity.MQMessageTemplate;
import cn.kafka.common.entity.MQMessageType;
import cn.kafka.common.utils.DateUtil;
import cn.kafka.common.utils.JSONUtils;
import cn.kafka.lib.ProducerManager;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class MQMessageTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(MQMessageTask.class);

    @Autowired
    private MQMessageDAO messageDAO;

    @Autowired
    private ProducerManager producerManager;

    @Scheduled(cron = "*/3 * * * * ?")
    public void execute() {
        List<MQMessage> list = messageDAO.selectNeedSendMessage();
        LOGGER.info("MQMessageTask list 's size:" + list.size());
        for (MQMessage message : list) {
            LOGGER.info("MQMessageTask message:" + message.toString());

            MQMessageTemplate template = new MQMessageTemplate();
            template.setData(JSONUtils.parseObject(message.getContent()));
            template.setId(message.getId());
            template.setMessageType(MQMessageType.SEND);
            template.setSendTime(new Date());

            //将自定义参数传递给消费者，消费者在消费完以后，可以根据这个自定义参数构建ackTopic，并且向该ackTopic发送消息
            template.setAckTopic(message.getAckTopic());

            producerManager.sendMessage(message.getTopic(), JSON.toJSONString(template));

            messageDAO.updateMessage(message.getId(), message.getMessageType(), DateUtil.getNextSendTime(message.getSendCnt()), new Date());
        }
    }
}

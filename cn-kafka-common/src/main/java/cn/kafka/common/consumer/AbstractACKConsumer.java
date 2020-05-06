package cn.kafka.common.consumer;

import cn.kafka.common.dao.MQMessageDAO;
import cn.kafka.common.entity.MQMessage;
import cn.kafka.common.entity.MQMessageStatus;
import cn.kafka.common.entity.MQMessageTemplate;
import cn.kafka.common.entity.MQMessageType;
import cn.kafka.common.utils.JSONUtils;
import cn.kafka.lib.Consumer;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public abstract class AbstractACKConsumer<T> extends Consumer {

    @Autowired
    private MQMessageDAO mqMessageDAO;

    protected abstract String getTopic();

    /**
     * 获取消息类
     *
     * @return
     */
    protected abstract Class<T> getMessageClass();

    /**
     * 具体业务实现
     *
     * @param data
     * @throws Exception
     */
    protected abstract void execute(T data) throws Exception;


    public String getGroupId() {
        return "ack";
    }

    protected void process(String msg) throws Exception {
        MQMessageTemplate template = JSON.parseObject(msg, MQMessageTemplate.class);
        if (!template.getMessageType().equals(MQMessageType.ACK)) {
            return;
        }

        MQMessage message = mqMessageDAO.selectOne(template.getId(), MQMessageType.SEND);
        if (message == null) {
            return;
        }

        if (message != null && message.getStatus().equals(MQMessageStatus.ACKED)) {
            return;
        }

        execute(JSONUtils.convert(template.getData(), getMessageClass()));
        mqMessageDAO.setAck(template.getId(), MQMessageType.SEND, template.getAckId(),
                JSONUtils.convertString(template.getData()), MQMessageStatus.ACKED);
    }
}

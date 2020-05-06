package cn.kafka.lib;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public abstract class Consumer implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    private ConsumerConnector connector;

    private ConsumerIterator<String, String> it;

    protected abstract String getTopic();

    protected abstract String getGroupId();

    protected abstract void process(String message) throws Exception;

    public void setConfig(Map<String, String> config) {
        String groupId = getGroupId();
        config.put("group.id", groupId);
    }

    public void init(Map<String, String> configMap) {
        String groupId = getGroupId();
        configMap.put("group.id", groupId);
        Properties props = new Properties();
        props.putAll(configMap);
        ConsumerConfig config = new ConsumerConfig(props);
        connector = kafka.consumer.Consumer.createJavaConsumerConnector(config);

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(getTopic(), new Integer(1));

        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

        Map<String, List<KafkaStream<String, String>>> consumerMap =
                connector.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
        KafkaStream<String, String> stream = consumerMap.get(getTopic()).get(0);
        it = stream.iterator();
    }

    public void consume() {
        LOGGER.info("consume start....");
        while (it.hasNext()) {
            MessageAndMetadata<String, String> mam = it.next();

            String msg = mam.message();

            try {
                process(msg);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            } finally {
                connector.commitOffsets();
            }

        }
        LOGGER.info("consume end....");
    }

    /**
     * 线程体
     */
    public void run() {
        consume();
    }

    /**
     * 销毁
     */
    public void destory() {
        if (null == connector) {
            connector.shutdown();
            connector = null;
        }
    }
}

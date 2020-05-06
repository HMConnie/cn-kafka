package cn.kafka.lib;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ProducerManager {
    private static Producer<String, String> producer;

    private static final ProducerManager instance = new ProducerManager();

    /**
     * 懒汉式单例
     */
    public static ProducerManager getInstance() {
        return instance;
    }

    /**
     * 初始化生产者
     */
    public void init(Map<String, String> producerConfig) {
        Properties props = new Properties();
        props.putAll(producerConfig);
        producer = new Producer<String, String>(new ProducerConfig(props));
    }

    /**
     * 发送消息
     */
    public void sendMessage(String topic, String msg) {
        producer.send(new KeyedMessage<String, String>(topic, msg));
    }

    /**
     * 批量发送消息
     */
    public void sendMessage(String topic, List<String> msgs) {
        for (String msg : msgs) {
            producer.send(new KeyedMessage<String, String>(topic, msg));
        }
    }

    /**
     * 销毁方法
     */
    public void destory() {
        // 关闭资源
        producer.close();
    }
}

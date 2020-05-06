package cn.kafka.lib;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaManagerFactory {
    private Map<String, String> producerConfig;

    private Map<String, String> consumerConfig;

    private List<Consumer> consumers;

    private ProducerManager producerManager;

    private ExecutorService executor= Executors.newCachedThreadPool();

    /**
     * 初始化方法
     */
    public void init() {
        // 初始化生产者
        producerManager=ProducerManager.getInstance();
        producerManager.init(producerConfig);
        // 初始化消费者
        for (Consumer consumer: consumers) {
            consumer.init(consumerConfig);
            executor.submit(consumer);
        }
    }

    /**
     * 销毁方法
     */
    public void destory() {
        // 销毁生产者
        producerManager.destory();
        // 销毁消费者
        for (Consumer consumer: consumers) {
            consumer.destory();
        }
        // 销毁线程池
        executor.shutdown();
    }

    public Map<String, String> getProducerConfig() {
        return producerConfig;
    }

    public void setProducerConfig(Map<String, String> producerConfig) {
        this.producerConfig=producerConfig;
    }

    public Map<String, String> getConsumerConfig() {
        return consumerConfig;
    }

    public void setConsumerConfig(Map<String, String> consumerConfig) {
        this.consumerConfig=consumerConfig;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<Consumer> consumers) {
        this.consumers=consumers;
    }

    public ProducerManager getProducerManager() {
        return producerManager;
    }

    public void setProducerManager(ProducerManager producerManager) {
        this.producerManager=producerManager;
    }
}

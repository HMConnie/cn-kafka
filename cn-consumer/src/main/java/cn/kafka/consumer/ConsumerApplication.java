package cn.kafka.consumer;

import cn.kafka.common.lock.DistributedLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:kafkaManagerConfig.xml", "classpath:database-config.xml"})
@SpringBootApplication
public class ConsumerApplication {

    @Bean(destroyMethod = "destroy")
    public DistributedLock getDistributedLock() throws Exception {
        return new DistributedLock("localhost:2181", "cn-consumer");
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}

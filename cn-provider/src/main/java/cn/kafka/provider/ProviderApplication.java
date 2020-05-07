package cn.kafka.provider;

import cn.kafka.common.lock.DistributedLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;


@ImportResource(locations = {"classpath:kafkaManagerConfig.xml", "classpath:database-config.xml"})
@SpringBootApplication
public class ProviderApplication {

    @Bean(destroyMethod = "destroy")
    public DistributedLock getDistributedLock() throws Exception {
        return new DistributedLock("localhost:2181", "cn-provider");
    }

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}

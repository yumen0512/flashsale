package com.ect.config;


import org.apache.kafka.clients.consumer.ConsumerConfig;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumeConfig {

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.105:9092");
        // 自动提交offSet到zk
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // 每隔多少时间提交
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        // ZooKeeper session 超时时间。如果在此时间内server没有向zookeeper发送心跳，zookeeper
        // 就会认为此节点已挂掉。 此值太低导致节点容易被标记死亡；若太高，.会导致太迟发现节点死亡。
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, "qianggou");
        // 当consumer再次启动将会从此offset开始继续消费.latest 和 earliest
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return propsMap;
    }

    @Bean
    public ConsumerFactory consumerFactory(){
        return new DefaultKafkaConsumerFactory(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory kafkaListenerContainerFactory(){

        ConcurrentKafkaListenerContainerFactory<String,String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        // 设置3个consume
        factory.setConcurrency(1);

        ContainerProperties props = factory.getContainerProperties();

        props.setPollTimeout(3000);

        props.setAckMode(AbstractMessageListenerContainer.AckMode.COUNT_TIME);
        props.setAckCount(1000);
        props.setAckTime(1000);

        props.setSyncCommits(true);

        props.setErrorHandler((thrownException, record) -> {
            System.out.printf("处理发生错误");
        });

        return factory;

    }



}

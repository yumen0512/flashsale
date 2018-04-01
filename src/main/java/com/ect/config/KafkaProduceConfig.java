package com.ect.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProduceConfig {

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // kafka机器ip
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.105:9092");
        // 发送失败后重新发送的次数，会改变发送顺序
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        // 批处理消息记录，改善性能，会浪费更多的内存空间
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 4096);
        // 用于追踪消息的源头
        props.put(ProducerConfig.CLIENT_ID_CONFIG,"qianggou");
        // 延迟发送
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // producer可以用来缓存数据的内存大小。如果数据产生速度大于向broker发送的速度，producer会阻塞或者抛出异常.
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 40960);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // producer需要server接收到数据之后发出的确认接收的信号，此项配置就是指procuder需要多少个这样的确认信号。此配置实际上代表了数据备
        // 份的可用性。以下设置为常用选项：（1）acks=0： 设置为0表示producer不需要等待任何确认收到的信息。副本将立即加到socket buffer并
        // 认为已经发送。没有任何保障可以保证此种情况下server已经成功接收数据，同时重试配置不会发生作用（因为客户端不知道是否失败）回馈的offset
        // 会总是设置为-1；（2）acks=1： 这意味着至少要等待leader已经成功将数据写入本地log，但是并没有等待所有follower是否成功写入。
        // 这种情况下，如果follower没有成功备份数据，而此时leader又挂掉，则消息会丢失。（3）acks=all： 这意味着leader需要等待所有备份都
        // 成功写入日志，这种策略会保证只要有一个备份存活就不会丢失数据。这是最强的保证。
        props.put(ProducerConfig.ACKS_CONFIG,"all");
        return props;
    }

    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate kafkaTemplate(){
        return new KafkaTemplate(producerFactory());
    }

}

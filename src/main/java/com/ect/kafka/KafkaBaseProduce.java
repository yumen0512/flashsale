package com.ect.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;


@EnableKafka
@Component
public class KafkaBaseProduce implements InitializingBean {

    private String topicName;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    protected Gson gson;

    protected Gson gsonBuild(){
        final GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }


    public void sendMsg(QiangGouMsg qiangGouMsg){

        String msg = gsonBuild().toJson(qiangGouMsg);

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName,msg);

        ListenableFutureCallback listenableFutureCallback = new ListenableFutureCallback() {
            @Override
            public void onSuccess(Object result) {
                System.out.println("消息发送成");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.printf("消息发送失败");
                ex.printStackTrace();
            }


        };

        future.addCallback(listenableFutureCallback);
    }


    public  String getTopicName(){
        return "QiangGouTopic";
    }


    @Override
    public void afterPropertiesSet() throws Exception {
       this.topicName = getTopicName();
    }


}

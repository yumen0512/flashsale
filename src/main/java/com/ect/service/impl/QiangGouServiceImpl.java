package com.ect.service.impl;

import com.ect.domain.DealDomain;
import com.ect.domain.mapper.DealMapper;
import com.google.gson.Gson;
import com.ect.kafka.KafkaBaseProduce;
import com.ect.kafka.QiangGouMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.ect.service.QiangGouService;

/**
 * 抢购活动service
 */
@Service
public class QiangGouServiceImpl implements QiangGouService {

    // 是否卖完
    private volatile boolean sellOut;

    @Autowired
    private KafkaBaseProduce kafkaBaseProduce;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DealMapper dealMapper;

    /**
     * 抢购活动逻辑
     * 1 接受到消息，将redis进行原子减1
     * 2 库存大于0，进行查表
     * 3 小于0 发送通知活动截止
     */
    @KafkaListener(id = "QiangGouTopic",topics = "QiangGouTopic",containerFactory = "kafkaListenerContainerFactory")
    public void qiangGouService(String str) {

        Gson gson = new Gson();
        QiangGouMsg qiangGouMsg = gson.fromJson(str,QiangGouMsg.class);

        // 查询是否卖完
        long num = redisTemplate.opsForValue().increment(qiangGouMsg.getProductId(),-1);

        if(num>=0){
            System.out.println("我要抢购了"+num);
            DealDomain dealDomain = new DealDomain(qiangGouMsg.getUserId(),qiangGouMsg.getProductId());
            dealMapper.save(dealDomain);
        }
        else{
            sellOut = true;
        }
    }

    @Override
    public void sendMsg(String userId, String productId) {
        // 如果卖完直接返回
        if (sellOut) {
            System.out.println("卖完啦");
            return;
        }

        QiangGouMsg qiangGouMsg = new QiangGouMsg(userId, productId);

        kafkaBaseProduce.sendMsg(qiangGouMsg);
        return;

    }

    @Override
    public void peiZhi(int num, String productId) {
        redisTemplate.opsForValue().set(productId,String.valueOf(num));
        sellOut = false;

    }
}

package com.denghuaijun.mq;

import com.alibaba.fastjson.JSON;
import com.denghuaijun.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * SmsService
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/7/8 11:18
 * @Description 发送短信服务
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup ="shop-user",topic = "order-topic")
public class SmsService implements RocketMQListener<Order> {
    @Override
    public void onMessage(Order o) {
        log.info("收到一个订单消息:{},接下来开始发送短信。。。", JSON.toJSONString(o));
    }
}

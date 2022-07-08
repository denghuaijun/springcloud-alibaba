package com.denghuaijun.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * RocketMQSendTest
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/7/7 16:15
 * @Description 消息消费者手撕java原生
 * 1、创建消息消费者。指定消费组组名
 * 2、指定namesrv地址
 * 3、指定消费者订阅的主题，标签
 * 4、设置回调函数，编写处理消息的方法
 * 5、启动消费者消费消息
 */
public class RocketMQConsumerTest {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //1、创建消息生产者。指定生产者所数组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("myconsumer-group");
        //2、指定namesrv地址
        consumer.setNamesrvAddr("192.168.201.52:9876");
        //3、指定消费者订阅的主题，标签
        consumer.subscribe("my_topic","*");
        //4、设置回调函数，编写处理消息的方法
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("接收到消息=======》："+list);
                //返回消费状态
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5、启动消费者
        consumer.start();
        System.out.println("consumer started");
    }
}

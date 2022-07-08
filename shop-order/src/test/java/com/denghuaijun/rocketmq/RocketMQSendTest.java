package com.denghuaijun.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * RocketMQSendTest
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/7/7 16:15
 * @Description 消息生产者手撕java原生
 * 1、创建消息生产者。指定生产者所数组名
 * 2、指定namesrv地址
 * 3、启动生产者
 * 4、创建消息地下，指定主题，标签和消息体
 * 5、发送消息
 * 6、关闭生产者
 */
public class RocketMQSendTest {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //1、创建消息生产者。指定生产者所数组名
        DefaultMQProducer producer = new DefaultMQProducer("myproducer-group");
        //2、指定namesrv地址
        producer.setNamesrvAddr("192.168.201.52:9876");
        //3、启动生产者
        producer.start();
        //4、创建消息对象，指定主题，标签和消息体
        Message message = new Message("my_topic","mytag","test rocketmq msg".getBytes());
        //5、发送消息
        SendResult sendResult = producer.send(message);
        System.out.println("生产者发送消息结果:"+sendResult);
        producer.shutdown();
    }
}

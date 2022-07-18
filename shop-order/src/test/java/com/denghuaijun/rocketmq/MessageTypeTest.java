package com.denghuaijun.rocketmq;

import com.denghuaijun.OrderApplication;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * MessageTypeTest
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/7/8 11:55
 * @Description MQ不同消息类型测试类
 * 1、 可靠同步消息发送
 * -指消息发送方发出消息后，会在收到接收方发回响应之后，才会发送下一个数据包
 * - 场景：重要邮件，报名短信，营销短信
 * 2、可靠异步发送
 * - 异步发送是指发送方发出数据后，不等待接受方发回响应，继续发送下个数据包，但是发送方通过回调接口的方式接受服务器响应并对结果进行处理
 * - 场景： 一般用于链路消耗较长，对RT响应时间较为敏感的场景
 * 3、单向发送
 * -指单向发送不等待，也没有回调函数处理
 * - 场景： 适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class MessageTypeTest {
    @Autowired
    RocketMQTemplate rocketMQTemplate;

    //同步消息
    @Test
    public void testSyncSend(){
        //参数一： topic 如果想要添加tag，可以使用"topic:tag"的写法
        //参数二： 消息内容
        SendResult sendResult = rocketMQTemplate.syncSend("test-sync-topic", "这是一条同步消息");
        System.out.println(sendResult);
    }

    //异步消息
    @Test
    public void testAsyncSend() throws InterruptedException {
        //参数一： topic 如果想要添加tag，可以使用"topic:tag"的写法
        //参数二： 消息内容
        //参数三：回调函数，处理返回结果
        rocketMQTemplate.asyncSend("test-async-topic", "这是一条异步消息", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println(throwable);
            }
        });
        //让线程不终止
        Thread.sleep(300000000);
    }

    //单向消息
    @Test
    public void testOneWay(){
        //参数一： topic 如果想要添加tag，可以使用"topic:tag"的写法
        //参数二： 消息内容
        rocketMQTemplate.sendOneWay("test-one-topic", "这是一条单向消息");

    }
}

package com.postss.common.message.queue.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.postss.common.message.queue.redis.entity.RedisMessageSend;
import com.postss.common.message.queue.redis.manager.RedisMessageQueueClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-security-redis.xml",
        "classpath:spring-security-service.xml" })
public class App {

    @Test
    public void test() {
        RedisMessageQueueClient m = new RedisMessageQueueClient();
        RedisMessageSend send = new RedisMessageSend();
        send.setBody("嘿嘿嘿");
        m.sendMessage(send);
        send.setBody("哈哈哈");
        m.sendMessage(send);
        System.out.println(m.getMessage());
        System.out.println(m.getMessage());
        System.out.println(m.getMessage());
    }
}

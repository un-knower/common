package com.postss.common.message.queue.activemq;

import javax.jms.Destination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQSend;
import com.postss.common.message.queue.activemq.producer.ProducerService;
import com.postss.common.message.queue.activemq.receiver.MqReceiverService;
import com.postss.common.util.MailUtil;

/**
 * 测试Spring JMS
 * 
 * 1.测试生产者发送消息
 * 
 * 2. 测试消费者接受消息
 * 
 * 3. 测试消息监听
 * 
 * 4.测试主题监听
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class TestJms {

    /**
     * 队列名queueManual
     */
    @Autowired
    private Destination queueDestinationManual;

    /**
     * 队列名queueAuto
     */
    @Autowired
    private Destination queueDestinationAuto;

    /**
     * 主题topicDestination
     */
    @Autowired
    @Qualifier("topicDestination")
    private Destination topicDestination;

    /**
     * 主题消息发布者
     */
    @Autowired
    private TopicProvider topicProvider;

    /**
     * 队列消息生产者
     */
    @Autowired
    @Qualifier("producerService")
    private ProducerService producerService;

    /**
     * 队列消息生产者
     */
    @Autowired
    @Qualifier("receiveService")
    private MqReceiverService receiverService;

    /**
     * 测试生产者向queueDestinationManual发送消息
     */
    @Test
    public void testProduce() {
        try {
            BaseActiveMQSend bam = new BaseActiveMQSend(
                    MailUtil.class.getMethod("sendMailByTo", String.class, String.class),
                    new Object[] { "332755645@qq.com", "嘿嘿嘿嘿嘿" });
            ActivemqHelper.sendTextMessage(bam);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    /**
     * 测试消费者从queueDestinationManual接受消息
     */
    @Test
    public void testConsume() {

        receiverService.receive();
    }

    /**
     * 测试消息监听
     * 1.生产者向队列queueDestinationAuto发送消息
     * 2.QueueAutoLinster监听队列，并消费消息
     */
    @Test
    public void testSend() {
        try {
            BaseActiveMQSend bam = new BaseActiveMQSend(
                    TestEntity.class.getMethod("testVoid", Integer[].class, String[].class),
                    new Object[] { new Integer[] { 1, 2 }, new String[] { "zhong", "wen" } });
            producerService.sendTextMessage(queueDestinationAuto, bam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试主题监听
     * 1.生产者向主题发布消息
     * 2.TopicMessageListener监听主题，并消费消息
     */
    @Test
    public void testTopic() throws Exception {
        topicProvider.publish(topicDestination, "Hello world!topicDestination");
    }

}
package com.postss.common.message.queue.activemq;

import javax.jms.Destination;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQEntity;
import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQReceive;
import com.postss.common.message.queue.activemq.messageInvoke.MessageInvoke;
import com.postss.common.message.queue.activemq.messageParse.MessageParse;
import com.postss.common.message.queue.activemq.producer.ProducerService;
import com.postss.common.util.SpringBeanUtil;

/**
 * 向jms发送消息
 * ClassName: ActivemqHelper 
 * @author jwSun
 * @date 2016年11月25日下午1:01:52
 */
public class ActivemqHelper {

    private static Logger log = Logger.getLogger(ActivemqHelper.class);

    public static void sendTextMessage(BaseActiveMQEntity baseActiveMQEntity) {
        try {
            setJms();
            Assert.notNull(producerService);
            Assert.notNull(queueDestinationAuto);
            Assert.notNull(queueDestinationManual);
            producerService.sendTextMessage(baseActiveMQEntity);
        } catch (Exception e) {
            noJmsInvoke(baseActiveMQEntity);
        }

    }

    public static void sendTextMessage(BaseActiveMQEntity baseActiveMQEntity, Destination destination) {
        try {
            setJms();
            Assert.notNull(producerService);
            Assert.notNull(queueDestinationAuto);
            Assert.notNull(queueDestinationManual);
            producerService.sendTextMessage(destination, baseActiveMQEntity);
        } catch (Exception e) {
            noJmsInvoke(baseActiveMQEntity);
        }
    }

    public static Object sendAndReceive(BaseActiveMQEntity baseActiveMQEntity) {
        try {
            setJms();
            Assert.notNull(producerService);
            Assert.notNull(queueDestinationAuto);
            Assert.notNull(queueDestinationManual);
            Object obj = producerService.sendAndReceive(baseActiveMQEntity);
            return obj;
        } catch (Exception e) {
            return noJmsInvoke(baseActiveMQEntity);
        }
    }

    /**
     * 当activemq没有开启时自动解析
     * @author jwSun
     * @date 2016年11月25日下午5:10:10
     * @param baseActiveMQSend
     */
    private static Object noJmsInvoke(BaseActiveMQEntity baseActiveMQEntity) {
        try {
            BaseActiveMQReceive activeMQReceive = jmsMessageParse
                    .parseObject(jmsMessageParse.parseString(baseActiveMQEntity), BaseActiveMQReceive.class);
            Assert.notNull(activeMQReceive.getClazz(), "调用类为空");
            Assert.notNull(activeMQReceive.getMethod(), "调用方法为空");
            return jmsMessageInvoke.invoke(activeMQReceive);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Destination queueDestinationManual;
    private static Destination queueDestinationAuto;
    private static ProducerService producerService;
    private static MessageParse jmsMessageParse;
    private static MessageInvoke jmsMessageInvoke;
    private static Boolean setJms = false;

    public static void setJms() {
        if (!setJms) {
            setSetJms(true);
            try {
                setQueueDestinationManual((Destination) SpringBeanUtil.getBean("queueDestinationManual"));
                setQueueDestinationAuto((Destination) SpringBeanUtil.getBean("queueDestinationAuto"));
                setProducerService((ProducerService) SpringBeanUtil.getBean("producerService"));
            } catch (Exception e) {
                log.info("jms服务未启动:" + e.getLocalizedMessage());
                throw new RuntimeException("jms服务未启动");
            }
        }
    }

    public static void setQueueDestinationManual(Destination queueDestinationManual) {
        ActivemqHelper.queueDestinationManual = queueDestinationManual;
    }

    public static void setQueueDestinationAuto(Destination queueDestinationAuto) {
        ActivemqHelper.queueDestinationAuto = queueDestinationAuto;
    }

    public static void setProducerService(ProducerService producerService) {
        ActivemqHelper.producerService = producerService;
    }

    public static void setJmsMessageParse(MessageParse jmsMessageParse) {
        ActivemqHelper.jmsMessageParse = jmsMessageParse;
    }

    public static void setJmsMessageInvoke(MessageInvoke jmsMessageInvoke) {
        ActivemqHelper.jmsMessageInvoke = jmsMessageInvoke;
    }

    public static void setSetJms(Boolean setJms) {
        ActivemqHelper.setJms = setJms;
    }

}

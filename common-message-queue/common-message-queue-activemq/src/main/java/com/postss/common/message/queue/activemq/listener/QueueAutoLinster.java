package com.postss.common.message.queue.activemq.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.postss.common.message.queue.activemq.ActivemqHelper;
import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQReceive;
import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQSend;
import com.postss.common.message.queue.activemq.messageInvoke.MessageInvoke;
import com.postss.common.message.queue.activemq.messageParse.MessageParse;
import com.postss.common.util.MailUtil;

public class QueueAutoLinster implements MessageListener {

    private static Logger log = Logger.getLogger(QueueAutoLinster.class);

    @Autowired
    private MessageParse jmsMessageParse;
    @Autowired
    private MessageInvoke jmsMessageInvoke;

    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            if (textMessage == null) {
                return;
            }
            BaseActiveMQReceive activeMQReceive = jmsMessageParse.parseObject(textMessage.getText(),
                    BaseActiveMQReceive.class);
            Assert.notNull(activeMQReceive.getClazz());
            Assert.notNull(activeMQReceive.getMethod());
            jmsMessageInvoke.invoke(activeMQReceive);
            ActivemqHelper
                    .sendTextMessage(
                            new BaseActiveMQSend(
                                    MailUtil.class.getMethod("sendMailByToFormJMS", String.class, String.class,
                                            Integer.class),
                                    new Object[] { "332755645@qq.com", "6666666666666666666666666666", 7 }),
                            message.getJMSReplyTo());
        } catch (Exception e) {
            log.info("解析失败", e);
        }
    }
}
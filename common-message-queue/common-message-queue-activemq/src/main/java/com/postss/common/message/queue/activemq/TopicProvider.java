package com.postss.common.message.queue.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class TopicProvider {

	private JmsTemplate topicJmsTemplate;

	/**
	 * 向指定的topic发布消息
	 * 
	 * @param topic
	 * @param msg
	 */
	public void publish(final Destination topic, final String msg) {
		topicJmsTemplate.send(topic, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}

	public void setTopicJmsTemplate(JmsTemplate topicJmsTemplate) {
		this.topicJmsTemplate = topicJmsTemplate;
	}

}
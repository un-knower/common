package com.postss.common.message.queue.activemq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *和队列监听的代码一样。
 */
public class TopicMessageListener implements MessageListener {

	public void onMessage(Message message) {
		TextMessage tm = (TextMessage) message;
		try {
			System.out.println("TopicMessageListener-----TopicMessageListener \t" + tm.getText());
		} catch (JMSException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
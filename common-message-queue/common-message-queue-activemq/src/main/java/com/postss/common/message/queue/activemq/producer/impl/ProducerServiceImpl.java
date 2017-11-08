package com.postss.common.message.queue.activemq.producer.impl;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQEntity;
import com.postss.common.message.queue.activemq.messageParse.MessageParse;
import com.postss.common.message.queue.activemq.producer.ProducerService;

/**
 * 队列消息产生者实现类
 * ClassName: MqProducerService 
 * @author jwSun
 * @date 2016年10月22日上午10:34:56
 */
public class ProducerServiceImpl implements ProducerService {

	private static Logger log = Logger.getLogger(ProducerServiceImpl.class);

	//spring注入配置模板
	private JmsTemplate jmsTemplate;

	//手动队列
	@Autowired
	private Destination queueDestinationManual;

	//自动队列
	@Autowired
	private Destination queueDestinationAuto;

	@Autowired
	private MessageParse jmsMessageParse;

	/**
	 * 向默认指定队列发送消息
	 * @author jwSun
	 * @date 2016年10月22日上午10:35:13
	 * @param destination 队列
	 * @param BaseActiveMQSend 发送对象
	 */
	@Override
	public void sendTextMessage(BaseActiveMQEntity baseActiveMQEntity) {
		final String message = jmsMessageParse.parseString(baseActiveMQEntity);
		try {
			jmsTemplate.send(new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(message);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向指定队列发送消息
	 * @author jwSun
	 * @date 2016年10月22日上午10:35:13
	 * @param destination 队列
	 * @param BaseActiveMQSend 发送对象
	 */
	@Override
	public void sendTextMessage(Destination destination, BaseActiveMQEntity baseActiveMQEntity) {
		final String message = jmsMessageParse.parseString(baseActiveMQEntity);
		try {
			jmsTemplate.send(destination, new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(message);
				}
			});
		} catch (Exception e) {
			log.error("向队列\t" + destination.toString() + "发送消息:\t" + message + "\t失败");
			e.printStackTrace();
		}
	}

	/**
	 * 向指定队列发送消息
	 * @author jwSun
	 * @date 2016年10月22日上午10:35:13
	 * @param destination 队列
	 * @param message 消息
	 */
	@Override
	public void sendTextMessage(Destination destination, final String message) {
		try {
			jmsTemplate.send(destination, new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(message);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			log.error("向队列\t" + destination.toString() + "发送消息:\t" + message + "\t失败。");
		}
	}

	/**
	 * 向默认队列发送消息
	 * @author jwSun
	 * @date 2016年10月22日上午10:35:32
	 * @param message 消息
	 */
	@Override
	public void sendTextMessage(final String message) {
		String destination = jmsTemplate.getDefaultDestination().toString();
		try {
			jmsTemplate.send(new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(message);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			log.error("向队列\t" + destination.toString() + "发送消息:\t" + message + "\t失败。");
		}

	}

	/**
	 * 发送并取得返回值
	 * @author jwSun
	 * @date 2016年12月22日上午11:04:38
	 * @param baseActiveMQEntity
	 * @return
	 */
	public Object sendAndReceive(BaseActiveMQEntity baseActiveMQEntity) {
		final String message = jmsMessageParse.parseString(baseActiveMQEntity);
		try {
			Message receive = jmsTemplate.sendAndReceive(new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(message);
				}
			});
			return ((TextMessage) receive).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设定jms模板
	 * @author jwSun
	 * @date 2016年10月22日上午10:35:54
	 * @param jmsTemplate jms模板
	 */
	@Override
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public void sendMapMessage(Destination destination, Map<String, Object> map) {
		try {
			jmsTemplate.convertAndSend(destination, map);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("发送map失败");
		}
	}

}
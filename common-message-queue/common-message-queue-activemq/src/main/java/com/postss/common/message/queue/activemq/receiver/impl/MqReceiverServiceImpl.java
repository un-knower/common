package com.postss.common.message.queue.activemq.receiver.impl;

import java.lang.reflect.Method;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.jms.Destination;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.Assert;

import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQReceive;
import com.postss.common.message.queue.activemq.messageInvoke.MessageInvoke;
import com.postss.common.message.queue.activemq.messageParse.MessageParse;
import com.postss.common.message.queue.activemq.receiver.MqReceiverService;

/**
 * 手动消息队列消费者
 * ClassName: MqReceiveServiceImpl 
 * @author jwSun
 * @date 2016年10月22日上午11:19:04
 */
public class MqReceiverServiceImpl implements MqReceiverService {

	private static Logger log = Logger.getLogger(MqReceiverServiceImpl.class);

	private static final ReentrantReadWriteLock.WriteLock lock = new ReentrantReadWriteLock().writeLock();

	private JmsTemplate jmsTemplateManual;

	@Autowired
	private Destination queueDestinationManual;

	@Autowired
	private MessageParse jmsMessageParse;

	@Autowired
	private MessageInvoke jmsMessageInvoke;

	/**
	 * 接收消息
	 * @author jwSun
	 * @date 2016年10月22日上午11:19:32
	 */
	//@Scheduled(fixedDelay = 1000)
	public void receive() {
		TextMessage textMessage = (TextMessage) jmsTemplateManual.receive();
		if (textMessage == null) {
			return;
		}
		try {
			lock.lock();
			BaseActiveMQReceive activeMQReceive = jmsMessageParse.parseObject(textMessage.getText(),
					BaseActiveMQReceive.class);
			Assert.notNull(activeMQReceive.getClazz(), "调用类为空");
			Assert.notNull(activeMQReceive.getMethod(), "调用方法为空");
			jmsMessageInvoke.invoke(activeMQReceive);
		} catch (Exception e) {
			log.info("接受消息失败");
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 接收消息
	 * @author jwSun
	 * @date 2016年10月22日上午11:19:32
	 */
	public void receive(Destination destination) {
		TextMessage textMessage = (TextMessage) jmsTemplateManual.receive();
		if (textMessage == null) {
			return;
		}
		try {
			lock.lock();
			BaseActiveMQReceive activeMQReceive = jmsMessageParse.parseObject(textMessage.getText(),
					BaseActiveMQReceive.class);
			Assert.notNull(activeMQReceive.getClazz());
			Assert.notNull(activeMQReceive.getMethod());
			invokeMethod(activeMQReceive);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	private void invokeMethod(BaseActiveMQReceive activeMQReceive) {
		try {
			Class<?> doClazz = Class.forName(activeMQReceive.getClazz());
			String[] parameterTypes = activeMQReceive.getParameterTypes();
			Class<?>[] clazzs = new Class<?>[parameterTypes.length];
			for (int i = 0; i < parameterTypes.length; i++) {
				clazzs[i] = Class.forName(parameterTypes[i]);
			}
			jmsMessageParse.parseParams(activeMQReceive, clazzs);
			Method doMethod = doClazz.getMethod(activeMQReceive.getMethod(), clazzs);
			doMethod.invoke(doClazz.newInstance(), activeMQReceive.getParams());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("invoke error");
		}
	}

	/**
	 * 接收Map
	 * @author jwSun
	 * @date 2016年10月22日上午11:19:32
	 */
	public void receiveMap() {
		try {
			Object message = jmsTemplateManual.receiveAndConvert();
			System.out.println(message);
		} catch (Exception e) {

		}
	}

	/**
	 * 更换消息队列模板
	 * @author jwSun
	 * @date 2016年10月22日上午11:21:04
	 * @param jmsTemplate
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplateManual = jmsTemplate;
	}
}
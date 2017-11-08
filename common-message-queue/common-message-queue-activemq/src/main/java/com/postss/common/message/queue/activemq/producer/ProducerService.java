package com.postss.common.message.queue.activemq.producer;

import java.util.Map;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQEntity;

/**
 * 队列消息产生者
 * ClassName: MqProducerService 
 * @author jwSun
 * @date 2016年10月22日上午10:34:56
 */
public interface ProducerService {

	/**
	 * 向默认队列发送消息
	 * @author jwSun
	 * @date 2016年11月25日下午12:57:23
	 * @param baseActiveMQEntity
	 */
	public void sendTextMessage(BaseActiveMQEntity baseActiveMQEntity);

	/**
	 * 向指定队列发送消息
	 * @author jwSun
	 * @date 2016年11月25日下午12:57:31
	 * @param destination
	 * @param baseActiveMQEntity
	 */
	public void sendTextMessage(Destination destination, BaseActiveMQEntity baseActiveMQEntity);

	/**
	 * 向指定队列发送消息
	 * @author jwSun
	 * @date 2016年10月22日上午10:35:13
	 * @param destination 队列
	 * @param message 消息
	 */
	public void sendTextMessage(Destination destination, final String message);

	/**
	 * 向默认队列发送消息
	 * @author jwSun
	 * @date 2016年10月22日上午10:35:32
	 * @param message 消息
	 */
	public void sendTextMessage(final String message);

	/**
	 * 向指定队列发送消息
	 * @author jwSun
	 * @date 2016年10月22日上午10:35:13
	 * @param destination 队列
	 * @param Map<String, Object> map 消息
	 */
	public void sendMapMessage(Destination destination, Map<String, Object> map);

	/**
	 * 发送消息并取得返回数据
	 * @author jwSun
	 * @date 2016年12月22日上午11:02:50
	 * @param baseActiveMQEntity
	 */
	public Object sendAndReceive(BaseActiveMQEntity baseActiveMQEntity);

	/**
	 * 设定jms模板
	 * @author jwSun
	 * @date 2016年10月22日上午10:35:54
	 * @param jmsTemplate jms模板
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate);

}

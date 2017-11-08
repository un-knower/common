package com.postss.common.message.queue.activemq.receiver;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

/**
 * 手动消息队列消费者
 * ClassName: MqReceiveServiceImpl 
 * @author jwSun
 * @date 2016年10月22日上午11:19:04
 */
public interface MqReceiverService {

	/**
	 * 通过指定接收消息
	 * @author jwSun
	 * @date 2016年10月22日上午11:22:12
	 * @param destination
	 */
	public void receive(Destination destination);

	/**
	 * 通过默认通道接收消息
	 * @author jwSun
	 * @date 2016年10月22日上午11:19:32
	 */
	public void receive();

	/**
	 * 更换消息队列模板
	 * @author jwSun
	 * @date 2016年10月22日上午11:21:04
	 * @param jmsTemplate
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate);
}

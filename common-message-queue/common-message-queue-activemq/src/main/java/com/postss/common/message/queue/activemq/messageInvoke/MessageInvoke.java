package com.postss.common.message.queue.activemq.messageInvoke;

import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQReceive;

/**
 * jms消息是调用器
 * ClassName: MessageInvoke 
 * @author jwSun
 * @date 2016年11月25日下午4:45:11
 */
public interface MessageInvoke {

	/**
	 * 执行方法
	 * @author jwSun
	 * @date 2016年11月25日下午4:48:00
	 * @param activeMQReceive
	 */
	public Object invoke(BaseActiveMQReceive activeMQReceive);

}

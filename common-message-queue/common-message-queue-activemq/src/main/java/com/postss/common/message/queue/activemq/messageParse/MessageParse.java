package com.postss.common.message.queue.activemq.messageParse;

import javax.jms.TextMessage;

import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQEntity;
import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQReceive;

/**
 * 消息解析接口
 * @author sjw
 * @date   2016年10月25日 下午3:59:26
 */
public interface MessageParse {

	/**
	 * 解析为指定bean
	 * @author sjw
	 * @date 2016年10月25日 下午4:43:41 
	 * @param textMessage
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T parseObject(TextMessage textMessage, Class<T> clazz) throws Exception;

	/**
	 * 解析为指定bean
	 * @author sjw
	 * @date 2016年10月25日 下午4:43:41 
	 * @param textMessage
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T parseObject(String text, Class<T> clazz) throws Exception;

	/**
	 * json接收参数如果为数组或list需要执行此方法获得正确参数 
	 * @author sjw
	 * @date 2016年10月25日 下午4:42:35 
	 * @param baseActiveMQReceive
	 * @param paramClazz
	 */
	public void parseParams(BaseActiveMQReceive baseActiveMQReceive, Class<?>[] paramClazz);

	/**
	 * 转化为字符串发送
	 * @author jwSun
	 * @date 2016年11月25日下午5:00:10
	 * @param BaseActiveMQEntity
	 * @return
	 */
	public String parseString(BaseActiveMQEntity BaseActiveMQEntity);

}

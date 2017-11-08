package com.postss.common.message.queue.activemq.messageInvoke.impl;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQReceive;
import com.postss.common.message.queue.activemq.messageInvoke.MessageInvoke;
import com.postss.common.message.queue.activemq.messageParse.MessageParse;

public class MethodInvoke implements MessageInvoke {

	private static Logger log = Logger.getLogger(MethodInvoke.class);

	@Autowired
	private MessageParse jmsMessageParse;

	@Override
	public Object invoke(BaseActiveMQReceive activeMQReceive) {
		try {
			Class<?> doClazz = Class.forName(activeMQReceive.getClazz());
			String[] parameterTypes = activeMQReceive.getParameterTypes();
			Class<?>[] clazzs = new Class<?>[parameterTypes.length];
			for (int i = 0; i < parameterTypes.length; i++) {
				clazzs[i] = Class.forName(parameterTypes[i]);
			}
			jmsMessageParse.parseParams(activeMQReceive, clazzs);
			Method doMethod = doClazz.getMethod(activeMQReceive.getMethod(), clazzs);
			return doMethod.invoke(doClazz.newInstance(), activeMQReceive.getParams());
		} catch (Exception e) {
			log.error("invoke error:" + JSONObject.toJSONString(activeMQReceive), e);
		}
		return null;
	}

}

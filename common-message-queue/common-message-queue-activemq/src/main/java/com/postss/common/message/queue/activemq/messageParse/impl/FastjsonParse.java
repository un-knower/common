package com.postss.common.message.queue.activemq.messageParse.impl;

import java.lang.reflect.Array;
import java.util.List;

import javax.jms.TextMessage;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQEntity;
import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQReceive;
import com.postss.common.message.queue.activemq.messageParse.MessageParse;

/**
 * com.alibaba.fastjson解析类
 * @author sjw
 * @date   2016年10月25日 下午2:37:04
 */
public class FastjsonParse implements MessageParse {

	@Override
	public <T> T parseObject(TextMessage textMessage, Class<T> clazz) throws Exception {
		if (textMessage == null) {
			return null;
		}
		Assert.isTrue(clazz.newInstance() instanceof BaseActiveMQReceive, "解析必须为BaseActiveMQReceive类或其子类");
		T receiveParse = JSONObject.parseObject(textMessage.getText(), clazz);
		return receiveParse;
	}

	@Override
	public void parseParams(BaseActiveMQReceive baseActiveMQReceive, Class<?>[] paramClazz) {
		Object[] params = new Object[baseActiveMQReceive.getParams().length];
		for (int j = 0; j < baseActiveMQReceive.getParams().length; j++) {
			if (baseActiveMQReceive.getParams()[j].getClass() == com.alibaba.fastjson.JSONArray.class) {
				List<Object> paramsList = (JSONArray) baseActiveMQReceive.getParams()[j];
				Object objArray = Array.newInstance(paramClazz[j].getComponentType(), paramsList.size());
				for (int i = 0; i < paramsList.size(); i++) {
					Array.set(objArray, i, paramsList.get(i));
				}
				params[j] = objArray;
			} else {
				params[j] = baseActiveMQReceive.getParams()[j];
			}
		}
		baseActiveMQReceive.setParams(params);
	}

	@Override
	public String parseString(BaseActiveMQEntity baseActiveMQEntity) {
		return JSONObject.toJSONString(baseActiveMQEntity);
	}

	@Override
	public <T> T parseObject(String text, Class<T> clazz) throws Exception {
		if (text == null) {
			return null;
		}
		Assert.isTrue(clazz.newInstance() instanceof BaseActiveMQReceive, "解析必须为BaseActiveMQReceive类或其子类");
		T receiveParse = JSONObject.parseObject(text, clazz);
		return receiveParse;
	}

}

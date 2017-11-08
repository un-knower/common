package com.postss.common.message.queue.activemq.messageParse.impl;

import javax.jms.TextMessage;

import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQEntity;
import com.postss.common.message.queue.activemq.messageEntity.BaseActiveMQReceive;
import com.postss.common.message.queue.activemq.messageParse.MessageParse;

public class ObjectMapperParse implements MessageParse {

    @Override
    public <T> T parseObject(TextMessage textMessage, Class<T> clazz) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T parseObject(String text, Class<T> clazz) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void parseParams(BaseActiveMQReceive baseActiveMQReceive, Class<?>[] paramClazz) {
        // TODO Auto-generated method stub

    }

    @Override
    public String parseString(BaseActiveMQEntity BaseActiveMQEntity) {
        // TODO Auto-generated method stub
        return null;
    }

    /*public static final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public <T> T parseObject(TextMessage textMessage, Class<T> clazz) throws Exception {
    	if (textMessage == null) {
    		return null;
    	}
    	Assert.isTrue(clazz.newInstance() instanceof BaseActiveMQReceive, "解析必须为BaseActiveMQReceive类或其子类");
    	return objectMapper.readValue(textMessage.getText().getBytes("utf-8"), clazz);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void parseParams(BaseActiveMQReceive baseActiveMQReceive, Class<?>[] paramClazz) {
    	Object[] params = new Object[baseActiveMQReceive.getParams().length];
    	for (int j = 0; j < baseActiveMQReceive.getParams().length; j++) {
    		if (baseActiveMQReceive.getParams()[j] instanceof java.util.List) {
    			List<Object> paramsList = (List<Object>) baseActiveMQReceive.getParams()[j];
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
    
    	return null;
    }
    
    @Override
    public <T> T parseObject(String text, Class<T> clazz) throws Exception {
    	Assert.isTrue(clazz.newInstance() instanceof BaseActiveMQReceive, "解析必须为BaseActiveMQReceive类或其子类");
    	return objectMapper.readValue(text.getBytes("utf-8"), clazz);
    }*/
}

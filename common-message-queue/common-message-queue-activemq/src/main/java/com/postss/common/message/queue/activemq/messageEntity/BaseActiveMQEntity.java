package com.postss.common.message.queue.activemq.messageEntity;

/**
 * 消息通讯基础类
 * @author sjw
 * @date   2016年10月25日 下午3:57:38
 */
public class BaseActiveMQEntity {

	protected String[] parameterTypes;
	protected Object[] params;
	protected String method;
	protected String clazz;

	public Object[] getParams() {
		return params;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}

	public String getMethod() {
		return method;
	}

	public String getClazz() {
		return clazz;
	}

	public BaseActiveMQEntity(String clazz, String method, String[] parameterTypes, Object[] params) {
		super();
		this.parameterTypes = parameterTypes;
		this.params = params;
		this.method = method;
		this.clazz = clazz;
	}

	public BaseActiveMQEntity() {
		super();
	}
}

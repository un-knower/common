package com.postss.common.message.queue.activemq.messageEntity;

import com.postss.common.message.queue.entity.BaseMessageReceive;

/**
 * 消息接收基础类
 * @author sjw
 * @date   2016年10月25日 下午3:58:41
 */
public class BaseActiveMQReceive extends BaseActiveMQEntity implements BaseMessageReceive {

    public void setParameterTypes(String[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public void setBody(Object body) {
        // TODO Auto-generated method stub

    }

}

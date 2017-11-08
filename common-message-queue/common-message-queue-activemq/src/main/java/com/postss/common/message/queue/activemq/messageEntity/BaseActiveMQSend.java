package com.postss.common.message.queue.activemq.messageEntity;

import java.lang.reflect.Method;

import org.springframework.util.Assert;

import com.postss.common.message.queue.entity.BaseMessageSend;

/**
 * 消息发送基础类
 * @author sjw
 * @date   2016年10月25日 下午3:58:24
 */
public class BaseActiveMQSend extends BaseActiveMQEntity implements BaseMessageSend {

    /** 
     *	消息构造器
     * @param clazz 	xxx.class
     * @param method	xxx.class.getMethod()
     * @param params 	new Object[]{}
     */
    public BaseActiveMQSend(Method doMethod, Object[] params) {
        super();
        try {
            Class<?>[] parameterTypesClazz = doMethod.getParameterTypes();
            Class<?> doClazz = doMethod.getDeclaringClass();
            Assert.isTrue(params.length == parameterTypesClazz.length, "传入参数个数需与方法个数一致!");
            for (int i = 0; i < parameterTypesClazz.length; i++) {
                Assert.isTrue(parameterTypesClazz[i].getName().equals(params[i].getClass().getName()),
                        "传入参数顺序需与方法参数顺序一致!");
            }
            if (parameterTypesClazz != null) {
                parameterTypes = new String[parameterTypesClazz.length];
                for (int i = 0; i < parameterTypesClazz.length; i++) {
                    parameterTypes[i] = parameterTypesClazz[i].getName();
                }
            }
            this.clazz = doClazz.getName();
            this.method = doMethod.getName();
            this.params = params;
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException();
        }
    }

    @Override
    public Object getBody() {
        // TODO Auto-generated method stub
        return null;
    }

}

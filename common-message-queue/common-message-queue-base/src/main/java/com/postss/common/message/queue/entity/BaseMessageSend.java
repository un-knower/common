package com.postss.common.message.queue.entity;

/**
 * 基础消息发送接口
 * @author jwSun
 * @date 2017年6月21日 下午3:37:25
 */
public interface BaseMessageSend extends BaseMessage {

    public Object getBody();

}

package com.postss.common.message.queue.interfaces;

import com.postss.common.message.queue.entity.BaseMessageReceive;
import com.postss.common.message.queue.entity.BaseMessageSend;

/**
 * 消息队列管理
 * @author jwSun
 * @date 2017年6月21日 下午3:30:22
 */
public interface MessageQueueClient<K extends BaseMessageSend, V extends BaseMessageReceive> {

    /**
     * 发送消息
     * @author jwSun
     * @date 2017年6月21日 下午3:45:15
     * @param messageSend messageSend
     * @return
     */
    public Object sendMessage(K messageSend);

    /**
     * 接收消息
     * @author jwSun
     * @date 2017年6月21日 下午3:42:42
     * @return
     */
    public V getMessage();

}

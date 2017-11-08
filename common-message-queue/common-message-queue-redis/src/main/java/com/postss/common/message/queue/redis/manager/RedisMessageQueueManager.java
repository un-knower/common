package com.postss.common.message.queue.redis.manager;

import com.alibaba.fastjson.JSONObject;
import com.postss.common.cache.redis.RedisUtil;
import com.postss.common.message.queue.entity.BaseMessageReceive;
import com.postss.common.message.queue.entity.BaseMessageSend;
import com.postss.common.message.queue.interfaces.MessageQueueManager;
import com.postss.common.message.queue.redis.entity.RedisMessageReceive;

public class RedisMessageQueueManager implements MessageQueueManager {

    private static final String messageQueueName = "message:queue:";

    @Override
    public <T extends BaseMessageSend> Object sendMessage(T messageSend) {
        if (messageSend == null) {
            return null;
        }
        RedisUtil.getJedisCommands().rpush(messageQueueName, JSONObject.toJSONString(messageSend));
        return true;
    }

    @Override
    public BaseMessageReceive getMessage() {
        return getMessageReal();
    }

    public RedisMessageReceive getMessageReal() {
        String message = RedisUtil.getJedisCommands().lpop(messageQueueName);
        if (message == null) {
            return null;
        }
        return JSONObject.parseObject(message, RedisMessageReceive.class);
    }

}

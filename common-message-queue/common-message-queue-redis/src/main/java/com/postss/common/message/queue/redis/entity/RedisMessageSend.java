package com.postss.common.message.queue.redis.entity;

import com.postss.common.message.queue.entity.BaseMessageSend;

public class RedisMessageSend implements BaseMessageSend {

    @Override
    public Object getBody() {
        return body;
    }

    private Object body;

    public void setBody(Object body) {
        this.body = body;
    }

}

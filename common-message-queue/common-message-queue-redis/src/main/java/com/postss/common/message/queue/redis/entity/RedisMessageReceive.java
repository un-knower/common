package com.postss.common.message.queue.redis.entity;

import com.postss.common.message.queue.entity.BaseMessageReceive;

public class RedisMessageReceive implements BaseMessageReceive {

    private Object body;

    public Object setBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

}

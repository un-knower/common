package com.postss.common.distributed.extend.org.springframework.session.data;

import org.springframework.data.redis.connection.RedisConnectionFactory;

public class RedisOperationsSessionRepository
        extends org.springframework.session.data.redis.RedisOperationsSessionRepository {

    public RedisOperationsSessionRepository(RedisConnectionFactory redisConnectionFactory) {
        super(redisConnectionFactory);
    }

}

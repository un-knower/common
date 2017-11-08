package com.postss.common.cache.redis.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.postss.common.cache.redis.dataSource.RedisDataSource;
import com.postss.common.log.util.LoggerUtil;

import redis.clients.jedis.ShardedJedis;

/**
 * redis动态代理类
 * 
 * @author jwSun
 * @date 2017年3月24日 上午11:40:56
 */
public class RedisInvocationHandler implements InvocationHandler {

    private final org.slf4j.Logger log = LoggerUtil.getLogger(RedisClientTemplate.class);

    private RedisDataSource redisDataSource;

    public RedisInvocationHandler(RedisDataSource redisDataSource) {
        super();
        this.redisDataSource = redisDataSource;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            log.error("未获取到{}对象", ShardedJedis.class.getName());
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getClass().getMethod(method.getName(), method.getParameterTypes())
                    .invoke(shardedJedis, args);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

}

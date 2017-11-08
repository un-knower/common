package com.postss.common.cache.redis.dataSource.impl;

import com.postss.common.cache.redis.dataSource.RedisDataSource;
import com.postss.common.log.util.LoggerUtil;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisDataSourceImpl implements RedisDataSource {

    private final org.slf4j.Logger log = LoggerUtil.getLogger(RedisDataSourceImpl.class);

    private ShardedJedisPool shardedJedisPool;

    @Override
    public ShardedJedis getRedisClient() {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis;
        } catch (Exception e) {
            log.error("[JedisDS] getRedisClent error:" + e.getMessage());
            if (null != shardedJedis)
                shardedJedis.close();
        }
        return null;
    }

    public RedisDataSourceImpl() {
        /* System.out.println(111); */
    }

    @Override
    public void returnResource(ShardedJedis shardedJedis) {
        shardedJedis.close();
    }

    @Override
    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        shardedJedis.close();
    }

    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

}

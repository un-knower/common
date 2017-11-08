package com.postss.common.cache.redis.dataSource;

import redis.clients.jedis.ShardedJedis;

public interface RedisDataSource {

	public ShardedJedis getRedisClient();

	public void returnResource(ShardedJedis shardedJedis);

	public void returnResource(ShardedJedis shardedJedis, boolean broken);

}

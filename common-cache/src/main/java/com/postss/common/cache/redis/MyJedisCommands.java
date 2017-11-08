package com.postss.common.cache.redis;

import com.postss.common.cache.DataCache;

import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.JedisCommands;

public interface MyJedisCommands extends DataCache, JedisCommands, BinaryJedisCommands {

}

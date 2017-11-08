package com.postss.common.cache.redis;

import com.postss.common.cache.CacheManager;
import com.postss.common.cache.DataCache;

public class RedisCacheManager implements CacheManager {

    @Override
    public DataCache getCache() {
        return RedisUtil.getJedisCommands();
    }

}

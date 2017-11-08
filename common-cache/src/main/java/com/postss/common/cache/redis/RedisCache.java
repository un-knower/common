package com.postss.common.cache.redis;

import com.postss.common.cache.WebCache;

public class RedisCache implements WebCache {

    @Override
    public Object getCache() {
        return RedisUtil.getJedisCommands();
    }

}

package com.postss.common.cache;

import com.postss.common.cache.redis.RedisUtil;

import redis.clients.jedis.JedisCommands;

public class CacheUtil {

    enum cache {
        REDIS(RedisUtil.class, "getJedisCommands"), MEMCACHED(JedisCommands.class, "getJedisCommands");

        public Class<?> clazz;
        public String method;

        private cache(Class<?> clazz, String method) {
            this.clazz = clazz;
            this.method = method;
        }
    }

    //private static String cacheChoose = PropertiesUtil.getProperty("cacheChoose");

    //private static String CACHE = StringUtil.notEmpty(cacheChoose) ? cacheChoose : cache.REDIS.toString();

    /*public static Object getCache() {
    	Object obj = null;
    	try {
    		cache c = cache.valueOf(CACHE);
    		obj = c.clazz.getMethod(c.method, null).invoke(null, null);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return obj;
    }*/
}

package com.postss.common.cache.redis.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.postss.common.cache.redis.MyJedisCommands;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

/**
 * redis发送模版,使用动态代理调用官方所有方法,没有实际方法
 * ClassName: RedisClientTemplate 
 * @author jwSun
 * @date 2016年12月10日下午1:47:41
 */
public class RedisClientTemplate implements MyJedisCommands {

    @Override
    public String set(byte[] key, byte[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] get(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean exists(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long persist(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String type(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long expire(byte[] key, int seconds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long pexpire(byte[] key, long milliseconds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long expireAt(byte[] key, long unixTime) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long pexpireAt(byte[] key, long millisecondsTimestamp) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long ttl(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean setbit(byte[] key, long offset, boolean value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean setbit(byte[] key, long offset, byte[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean getbit(byte[] key, long offset) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long setrange(byte[] key, long offset, byte[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getrange(byte[] key, long startOffset, long endOffset) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getSet(byte[] key, byte[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long setnx(byte[] key, byte[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String setex(byte[] key, int seconds, byte[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long decrBy(byte[] key, long integer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long decr(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long incrBy(byte[] key, long integer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double incrByFloat(byte[] key, double value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long incr(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long append(byte[] key, byte[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] substr(byte[] key, int start, int end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long hset(byte[] key, byte[] field, byte[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] hget(byte[] key, byte[] field) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long hsetnx(byte[] key, byte[] field, byte[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String hmset(byte[] key, Map<byte[], byte[]> hash) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<byte[]> hmget(byte[] key, byte[]... fields) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long hincrBy(byte[] key, byte[] field, long value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double hincrByFloat(byte[] key, byte[] field, double value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean hexists(byte[] key, byte[] field) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long hdel(byte[] key, byte[]... field) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long hlen(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> hkeys(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<byte[]> hvals(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<byte[], byte[]> hgetAll(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long rpush(byte[] key, byte[]... args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long lpush(byte[] key, byte[]... args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long llen(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<byte[]> lrange(byte[] key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String ltrim(byte[] key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] lindex(byte[] key, long index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String lset(byte[] key, long index, byte[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long lrem(byte[] key, long count, byte[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] lpop(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] rpop(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long sadd(byte[] key, byte[]... member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> smembers(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long srem(byte[] key, byte[]... member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] spop(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> spop(byte[] key, long count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long scard(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean sismember(byte[] key, byte[] member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] srandmember(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<byte[]> srandmember(byte[] key, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long strlen(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zadd(byte[] key, double score, byte[] member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrange(byte[] key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zrem(byte[] key, byte[]... member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double zincrby(byte[] key, double score, byte[] member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zrank(byte[] key, byte[] member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zrevrank(byte[] key, byte[] member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrevrange(byte[] key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zcard(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double zscore(byte[] key, byte[] member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<byte[]> sort(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zcount(byte[] key, double min, double max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zcount(byte[] key, byte[] min, byte[] max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zremrangeByRank(byte[] key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zremrangeByScore(byte[] key, double start, double end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zlexcount(byte[] key, byte[] min, byte[] max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long lpushx(byte[] key, byte[]... arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long rpushx(byte[] key, byte[]... arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<byte[]> blpop(byte[] arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<byte[]> brpop(byte[] arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long del(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] echo(byte[] arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long move(byte[] key, int dbIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long bitcount(byte[] key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long bitcount(byte[] key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long pfadd(byte[] key, byte[]... elements) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long pfcount(byte[] key) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String set(String key, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, long time) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String get(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean exists(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long persist(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String type(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long expire(String key, int seconds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long pexpire(String key, long milliseconds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long expireAt(String key, long unixTime) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long pexpireAt(String key, long millisecondsTimestamp) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long ttl(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean setbit(String key, long offset, boolean value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean setbit(String key, long offset, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean getbit(String key, long offset) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long setrange(String key, long offset, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getrange(String key, long startOffset, long endOffset) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSet(String key, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long setnx(String key, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String setex(String key, int seconds, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long decrBy(String key, long integer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long decr(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long incrBy(String key, long integer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double incrByFloat(String key, double value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long incr(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long append(String key, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String substr(String key, int start, int end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long hset(String key, String field, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String hget(String key, String field) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long hsetnx(String key, String field, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long hincrBy(String key, String field, long value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean hexists(String key, String field) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long hdel(String key, String... field) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long hlen(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> hkeys(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> hvals(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long rpush(String key, String... string) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long lpush(String key, String... string) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long llen(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String ltrim(String key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String lindex(String key, long index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String lset(String key, long index, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long lrem(String key, long count, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String lpop(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String rpop(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long sadd(String key, String... member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> smembers(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long srem(String key, String... member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String spop(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> spop(String key, long count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long scard(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean sismember(String key, String member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String srandmember(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> srandmember(String key, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long strlen(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zadd(String key, double score, String member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zrem(String key, String... member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double zincrby(String key, double score, String member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zrank(String key, String member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zrevrank(String key, String member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zcard(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double zscore(String key, String member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> sort(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> sort(String key, SortingParams sortingParameters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zcount(String key, double min, double max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zcount(String key, String min, String max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zremrangeByScore(String key, double start, double end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zremrangeByScore(String key, String start, String end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zlexcount(String key, String min, String max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long zremrangeByLex(String key, String min, String max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long lpushx(String key, String... string) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long rpushx(String key, String... string) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> blpop(String arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> blpop(int timeout, String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> brpop(String arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> brpop(int timeout, String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long del(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String echo(String string) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long move(String key, int dbIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long bitcount(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long bitcount(String key, long start, long end) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ScanResult<String> sscan(String key, int cursor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ScanResult<Tuple> zscan(String key, int cursor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ScanResult<String> sscan(String key, String cursor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long pfadd(String key, String... elements) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long pfcount(String key) {
        // TODO Auto-generated method stub
        return 0;
    }

}

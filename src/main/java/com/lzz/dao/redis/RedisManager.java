package com.lzz.dao.redis;

/**
 * Created by lzz on 2018/3/19.
 */
public class RedisManager {
    private RedisClientBase redisClient;
    public RedisClientBase factory(String address){
        redisClient = new JedisClient(address);
        return redisClient;
    }
}

package com.lzz.dao.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import java.util.List;

/**
 * Created by lzz on 2018/3/19.
 */
public class JedisClient extends RedisClientBase implements IRedis {
    private Jedis jedis;
    public JedisClient(String address){
        String[] metas = address.split(",");
        if( metas.length > 0 ){
            String host = metas[0].split(":")[0];
            int port = Integer.parseInt(metas[0].split(":")[1]);
            jedis = new Jedis(host, port);
        }
        if( metas.length == 2 ){
            separator = metas[1];
            if( metas[1].equals("l") ){
                separator = "|";
            }
        }
    }

    @Override
    public List<String> childrenList(String path) throws Exception {
        List<String> childrens = childrenList( this, path, false);
        chidrenMap.put( path, childrens.size());
        return childrens;
    }

    @Override
    public void close() {
        if( null != jedis ){
            try {
                jedis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    ScanResult<String> scan(String cursor, ScanParams params) {
        ScanResult<String> scanResult = jedis.scan( cursor, params );
        return scanResult;
    }
}

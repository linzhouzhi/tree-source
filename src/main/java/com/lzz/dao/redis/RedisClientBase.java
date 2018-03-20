package com.lzz.dao.redis;

import com.google.common.collect.Lists;
import com.lzz.dao.SourceBase;
import org.springframework.util.StringUtils;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lzz on 2018/3/20.
 */
abstract class RedisClientBase extends SourceBase {
    protected String separator;
    abstract ScanResult<String> scan(String cursor, ScanParams params);


    public List<String> childrenList(RedisClientBase redisClient, String path, boolean hasChidren){
        List<String> resList = new ArrayList<>();
        Set<String> childrens = new HashSet<>();
        String cursor = "0";
        ScanParams params = new ScanParams();
        params.count(200);
        String matchKey = "*";
        int keyLoc = 0;
        if( path.equals("/") ){
            params.match( matchKey );
        }else if( path.split("/").length  == 2 ){
            matchKey = path.split("/")[1];
            String matchStr = matchKey + "*";
            params.match( matchStr );
            keyLoc = 1;
        }else{
            return resList;
        }
        boolean isBreak = false;
        for( int i = 0; i < 10; i++ ){
            ScanResult<String> scanResult = redisClient.scan( cursor, params );
            cursor = scanResult.getStringCursor();
            for(String key : scanResult.getResult()){
                if( StringUtils.isEmpty(separator) ){
                    if( !key.equals(matchKey) ){
                        if( path.equals("/") && key.length() > 15 ){
                            key = key.substring(0, 15);
                        }
                        childrens.add( key );
                    }
                }else{
                    String[] tmpKey = key.split( separator );
                    if( tmpKey.length > 1 ){
                        childrens.add( tmpKey[ keyLoc ] ); // 第一次 / 用分隔符前的，第二次用分隔符后的
                    }else{
                        if( !tmpKey[0].equals(matchKey) ){
                            String tmp = tmpKey[0];
                            if( path.equals("/") && tmp.length() > 15 ){
                                tmp = tmp.substring(0, 15);
                            }
                            childrens.add( tmp);
                        }
                    }
                }
                if( hasChidren && childrens.size() > 0 ){   //判断有没有 子节点
                    isBreak = true;
                    break;
                }
                if( childrens.size() > 200 ){
                    isBreak = true;
                    break;
                }
            }
            if( isBreak ){
                break;
            }
        }
        resList = Lists.newArrayList( childrens );
        return resList;
    }
}

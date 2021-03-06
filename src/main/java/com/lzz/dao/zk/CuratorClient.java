package com.lzz.dao.zk;

import com.lzz.dao.SourceBase;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lzz on 2018/3/18.
 */
@Component
public class CuratorClient extends SourceBase implements IZookeeperClient {
    private CuratorFramework client;

    public CuratorClient(){}

    public CuratorClient(String zk){
        initClient(zk);
    }
    public void initClient(String zk){
         client = CuratorFrameworkFactory.newClient(
                zk,
                new RetryNTimes(10, 5000)
        );
        client.start();
    }


    @Override
    public List<String> childrenList(String path) throws Exception {
        List<String> childrenList = this.client.getChildren().forPath( path );
        chidrenMap.put(path, childrenList.size());
        return childrenList;
    }

    @Override
    public void close() {
        this.client.close();
    }
}

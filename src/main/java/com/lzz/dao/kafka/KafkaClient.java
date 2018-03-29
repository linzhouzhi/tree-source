package com.lzz.dao.kafka;

import com.google.common.collect.Lists;
import com.lzz.dao.SourceBase;
import com.lzz.dao.zk.CuratorClient;
import com.lzz.util.KafkaConsumerUtil;

import java.util.*;

/**
 * Created by lzz on 2018/3/19.
 */
public class KafkaClient extends SourceBase implements IMQClient {
    private static final String CONSUMER_PATH = "/consumers";
    private Map<String, Set<String>> topicConsumerMap = new HashMap<>();
    private KafkaConsumerUtil kafkaConsumerUtil;
    private SourceBase curatorClient;

    public KafkaClient(){

    }
    public KafkaClient(String zk){
        curatorClient = new CuratorClient(zk);
        initTopicConsumerMap();
    }


    public void initZkClient(String zk){
        curatorClient = new CuratorClient(zk);
        initTopicConsumerMap();
    }


    @Override
    public List<String> query(List<String> seeds, int port, String topic, List<Integer> partitions, String message) {
        kafkaConsumerUtil = new KafkaConsumerUtil();
        List<String> resList = kafkaConsumerUtil.run(topic, partitions, seeds, port, message);
        return resList;
    }

    private void initTopicConsumerMap(){

        try {
            List<String> cList = curatorClient.childrenList( CONSUMER_PATH );
            for(String consumer : cList){
                List<String> topics = curatorClient.childrenList( CONSUMER_PATH + "/" + consumer + "/offsets" );
                for(String topic : topics){
                    if( topicConsumerMap.get( topic ) == null ){
                        topicConsumerMap.put( topic, new HashSet<>());
                    }
                    topicConsumerMap.get( topic ).add( consumer );
                }
            }
        } catch (Exception ignore) {

        }
    }


    @Override
    public List<String> childrenList(String path) throws Exception {
        List<String> cList = new ArrayList<>();
        if( path.equals("/") ){
            cList = curatorClient.childrenList("/brokers/topics");
        }else if( path.split("/").length == 2 ){
            Set<String> consumerList = topicConsumerMap.get( path.split("/")[1] );
            if( null != consumerList ){
                cList = Lists.newArrayList(consumerList);
            }
        }
        chidrenMap.put( path, cList.size());
        return cList;
    }

    @Override
    public void close() {
        this.curatorClient.close();
    }

}

package com.lzz.util;

import com.lzz.dao.kafka.KafkaClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzz on 2018/3/29.
 */
public class KafkaConsumerUtilTest {
    @Test
    public void testKafka(){
        KafkaConsumerUtil kafkaConsumerUtil = new KafkaConsumerUtil();
        String topic = "test";
        Integer partition = 0;
        List<String> seeds = new ArrayList<String>();
        seeds.add("127.0.0.1");
        Integer port = 9092;
        List<Integer> paritions = new ArrayList<>();
        paritions.add(0);
        paritions.add(1);
        try {
            String msg = "F";
            KafkaClient kafkaClient = new KafkaClient();
            List<String> resList = kafkaClient.query(seeds, port, topic, paritions, msg);
            System.out.println( resList );
        } catch (Exception e) {
            System.out.println("Oops:" + e);
            e.printStackTrace();
        }
    }
}

package com.lzz.logic;

import com.lzz.dao.kafka.IMQClient;
import com.lzz.dao.kafka.KafkaClient;
import com.lzz.model.KafkaQueryParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lzz on 2018/3/29.
 */
@Component
public class KafkaLogic {
    private IMQClient kafkaClient;

    public List<String> query(KafkaQueryParam param) {
        kafkaClient = new KafkaClient();
        return kafkaClient.query( param.getSeeds(), param.getPort(), param.getTopic(), param.getPartitions(), param.getMsg() );
    }
}

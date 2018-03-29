package com.lzz.dao.kafka;

import java.util.List;

/**
 * Created by lzz on 2018/3/19.
 */
public interface IMQClient {
    List<String> query(List<String> seeds, int port, String topic, List<Integer> partition, String message);
}

package com.lzz.model;

import java.util.List;

/**
 * Created by lzz on 2018/3/29.
 */
public class KafkaQueryParam {
    private List<String> seeds;
    private int port;
    private String topic;
    private List<Integer> partitions;
    private String msg;

    public List<String> getSeeds() {
        return seeds;
    }

    public void setSeeds(List<String> seeds) {
        this.seeds = seeds;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Integer> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<Integer> partitions) {
        this.partitions = partitions;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

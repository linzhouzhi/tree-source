package com.lzz.dao.hbase;

import com.google.common.collect.Lists;
import com.lzz.dao.SourceBase;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzz on 2018/3/20.
 */
public class HbaseClient extends SourceBase {
    private Connection connection;
    public HbaseClient(String address) throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", address);
        connection = ConnectionFactory.createConnection(configuration);
    }

    @Override
    public List<String> childrenList(String path) throws Exception {
        List<String> childrens = new ArrayList<>();
        HBaseAdmin hBaseAdmin = (HBaseAdmin) connection.getAdmin();
        if( path.equals("/") ){
            String[] tables = hBaseAdmin.getTableNames();
            childrens = Lists.newArrayList( tables );
        }else if( path.split("/").length == 2 ){
            TableName tableName = TableName.valueOf( path.split("/")[1] );
            HColumnDescriptor[] hColumnDescriptor = hBaseAdmin.getTableDescriptor( tableName ).getColumnFamilies();
            for(HColumnDescriptor cf : hColumnDescriptor){
                childrens.add(Bytes.toString(cf.getName()) );
            }
        }
        chidrenMap.put( path, childrens.size() );
        return childrens;
    }

    @Override
    public void close() {
        if( this.connection == null || this.connection.isClosed() ){
            return;
        }
        try {
            this.connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

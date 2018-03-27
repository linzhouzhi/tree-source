package com.lzz.logic;

import com.lzz.dao.sqldb.ISqlDb;
import com.lzz.dao.sqldb.Mysql;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by lzz on 2018/3/27.
 */
@Component
public class MysqlLogic {

    private ISqlDb mysql;

    public List query(String address, String database, String sql) throws Exception {
        mysql = new Mysql(address, database);
        List<Map<String, String>> list = mysql.query( database, sql );
        return list;
    }
}

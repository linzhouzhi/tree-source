package com.lzz.dao.sqldb;

import com.lzz.model.SqlDbType;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzz on 2018/3/19.
 */

public class Mysql extends SqlDb implements ISqlDb {
    protected static SqlDbType sqlDbType = SqlDbType.mysql;
    public Mysql(String address) throws Exception {
        super( sqlDbType, address);
    }
    public Mysql(String address, String database) throws Exception {
        super( sqlDbType, address, database);
    }

    @Override
    public List<Map<String, String>> query(String database, String sql) throws SQLException {
        List<Map<String, String>> resList = new ArrayList<>();
        ResultSet resultSet = this.stmt.executeQuery( sql );
        ResultSetMetaData resultSetMD = resultSet.getMetaData();
        while ( resultSet.next() ){
            Map<String, String> item = new HashMap<>();
            for (int i = 1; i <= resultSetMD.getColumnCount(); i++) {
                String column = resultSetMD.getColumnName(i);
                item.put( column, resultSet.getString( column ));
            }
            resList.add( item );
        }
        return resList;
    }
}

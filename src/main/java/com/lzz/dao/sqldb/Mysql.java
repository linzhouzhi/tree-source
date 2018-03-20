package com.lzz.dao.sqldb;

import com.lzz.model.SqlDbType;

/**
 * Created by lzz on 2018/3/19.
 */
public class Mysql extends SqlDb implements ISqlDb {
    protected static SqlDbType sqlDbType = SqlDbType.mysql;
    public Mysql(String address) throws Exception {
        super( sqlDbType, address);
    }
}

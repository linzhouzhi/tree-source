package com.lzz.dao.sqldb;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by lzz on 2018/3/19.
 */

public interface ISqlDb {
    List<Map<String, String>> query(String database, String sql) throws SQLException;
}

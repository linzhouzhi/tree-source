package com.lzz.dao.sqldb;

import com.lzz.dao.SourceBase;
import com.lzz.model.SqlDbType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by lzz on 2018/3/19.
 */
public abstract class SqlDb extends SourceBase{
    protected SqlDbType sqlDbType;
    protected Connection conn = null;
    protected Statement stmt = null;
    protected String dbUrl;
    protected String username;
    protected String password;
    public SqlDb(SqlDbType sqlDbType, String address) throws Exception {
        this.sqlDbType = sqlDbType;
        initMeta( address );
        conn = DriverManager.getConnection(this.dbUrl, this.username, this.password);
        stmt = conn.createStatement();
    }

    private void initMeta(String address) throws ClassNotFoundException {
        String[] metas = address.split(",");
        switch (this.sqlDbType){
            case mysql:
                Class.forName("com.mysql.jdbc.Driver");
                this.dbUrl = "jdbc:mysql://" + metas[0];
                break;
        }
        this.username = metas[1];
        this.password = metas[2];
    }



    public void close() {
        try {
            stmt.close();
            conn.close();
        }catch (Exception e){

        }
    }
}

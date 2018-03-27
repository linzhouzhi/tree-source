package com.lzz.dao.sqldb;

import com.lzz.dao.SourceBase;
import com.lzz.model.SqlDbType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public SqlDb(SqlDbType sqlDbType, String address, String database) throws ClassNotFoundException, SQLException {
        this.sqlDbType = sqlDbType;
        initMeta( address );
        conn = DriverManager.getConnection(this.dbUrl + "/" + database, this.username, this.password);
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


    @Override
    public List<String> childrenList(String path) throws Exception {
        List<String> childrens = new ArrayList<>();
        ResultSet rs = null;
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            if( path.equals("/") ){ // databases;
                rs = metaData.getCatalogs();
                while (rs.next()) {
                    childrens.add( rs.getString("TABLE_CAT") );
                }
            }else if( path.split("/").length == 2 ){ // tables
                rs = metaData.getTables( path.split("/")[1], null, "%", new String[]{"TABLE"});
                while (rs.next()) {
                    childrens.add( rs.getString("TABLE_NAME") );
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if( rs != null ){
                rs.close();
            }
        }
        chidrenMap.put(path, childrens.size());
        return childrens;
    }

    @Override
    protected String nodeType(String childPath, int childrenNum) {
        String type = "root";
        if( childPath.split("/").length == 2 ){
            type = "database";
        }else if( childPath.split("/").length == 3 ){
            type = "table";
        }
        return type;
    }

    @Override
    public void close() {
        try {
            stmt.close();
            conn.close();
        }catch (Exception e){

        }
    }
}

package com.lzz.dao.sqldb;

import com.lzz.model.SqlDbType;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzz on 2018/3/19.
 */
public class Mysql extends SqlDb implements ISqlDb {
    protected static SqlDbType sqlDbType = SqlDbType.mysql;
    public Mysql(String address) throws Exception {
        super( sqlDbType, address);
    }

    @Override
    public int getNumChildren(String childPath) throws Exception {
        int num = this.childrenList( childPath ).size();
        return num;
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
                rs = metaData.getTables( path.split("/")[1], this.username, "%", new String[]{"TABLE"});
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
        return childrens;
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

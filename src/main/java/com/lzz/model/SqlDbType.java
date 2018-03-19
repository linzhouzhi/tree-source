package com.lzz.model;

/**
 * Created by lzz on 2018/3/19.
 */
public enum  SqlDbType {
    mysql("mysql"), sqlServer("sqlServer");
    private String name;
    SqlDbType(String sqldb) {
        this.name = sqldb;
    }
}

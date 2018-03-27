package com.lzz.util;

import com.lzz.logic.MysqlLogic;
import org.junit.Test;

import java.util.List;

/**
 * Created by lzz on 2018/3/27.
 */
public class MysqlLogicTest {
    @Test
    public void test001() throws Exception {
        MysqlLogic logic = new MysqlLogic();
        List l = logic.query("127.0.0.1,root,root", "mosquito", "select * from logs limit 10");
        System.out.println( l );
    }
}

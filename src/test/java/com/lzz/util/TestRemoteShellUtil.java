package com.lzz.util;

import org.junit.Test;

/**
 * Created by lzz on 2018/3/20.
 */
public class TestRemoteShellUtil {
    @Test
    public void test001(){
        String res = RemoteShellUtil.localExec("ls /");
        System.out.println( res );
    }

    @Test
    public void test002(){
        RemoteShellUtil remoteShellUtil = new RemoteShellUtil("127.0.0.1", "lzz", "363216");
        String res = remoteShellUtil.execRemote("ls /");
        System.out.println( res );
    }
}

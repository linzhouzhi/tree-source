package com.lzz.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lzz on 2018/3/20.
 */
public class RemoteShellUtil {
    private Connection conn;
    private String ip;
    private String username;
    private String password;

    public RemoteShellUtil(String ip, String username, String password){
        this.ip = ip;
        this.username = username;
        this.password = password;
    }

    public boolean login() throws IOException {
        conn = new Connection(ip);
        conn.connect();
        return conn.authenticateWithPassword(username, password);
    }

    public static String localExec(String cmd){
        String result = "";
        try{
            String[] cmds = {"/bin/sh", "-c", cmd};
            Process ps = Runtime.getRuntime().exec( cmds );
            InputStream in = ps.getInputStream();
            result = processStdout(in);
            InputStream errorIn = ps.getErrorStream();
            result += processStdout(errorIn);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String execRemote(String cmds){
        String result = "";
        try {
            if( login() ){
                Session session = conn.openSession();
                session.execCommand( cmds );
                InputStream in = session.getStdout();
                result = processStdout(in);
                InputStream errorIn = session.getStderr();
                result += processStdout(errorIn);
            }
        }catch (Exception e){

        }
        return result;
    }

    public static String processStdout(InputStream in){
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ( (line = reader.readLine()) != null ){
                sb.append( line + "\n" );
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public void close(){
        conn.close();
    }
}

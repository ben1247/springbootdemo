package com.shuyun.sbd.utils.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Component:
 * Description:
 * Date: 15/8/23
 *
 * @author yue.zhang
 */
public class ThreadedEncoServer {

    public static void main(String [] args){
        try{
            int i = 1;
            ServerSocket ss = new ServerSocket(8189);
            while(true){
                Socket incoming = ss.accept();
                System.out.println(String.format("Spawning %s",i));
                Runnable r = new ThreadEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i ++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }



}

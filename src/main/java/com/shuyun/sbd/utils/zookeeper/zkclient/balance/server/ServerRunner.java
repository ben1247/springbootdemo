package com.shuyun.sbd.utils.zookeeper.zkclient.balance.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/11/13
 *
 * @author yue.zhang
 */
public class ServerRunner {

    private static final int SERVER_QTY = 2;

    private static final String ZOOKEEPER_PATH = "127.0.0.1:2181";

    private static final String SERVERS_PATH = "/servers";

    public static void main(String [] args){

        List<Thread> threadList = new ArrayList<>();

        for(int i = 0 ; i < SERVER_QTY; i++){
            final Integer count = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ServerData sd = new ServerData();
                    sd.setBalance(0);
                    sd.setHost("127.0.0.1");
                    sd.setPort(6000+count);

                    Server server = new ServerImpl(ZOOKEEPER_PATH,SERVERS_PATH,sd);
                    server.bind();
                }
            });
            threadList.add(thread);
            thread.start();
        }


        for(int i = 0 ; i < threadList.size(); i++){
            try {
                threadList.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

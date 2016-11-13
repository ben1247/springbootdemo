package com.shuyun.sbd.utils.zookeeper.zkclient.balance.client;

import com.shuyun.sbd.utils.zookeeper.zkclient.balance.server.ServerData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/11/13
 *
 * @author yue.zhang
 */
public class ClientRunner {

    private static final int CLIENT_QTY = 3;

    private static final String ZOOKEEPER_SERVER = "127.0.0.1:2181";

    private static final String SERVERS_PATH = "/servers";

    public static void main(String [] args){
        List<Thread> threadList = new ArrayList<>(CLIENT_QTY);
        final List<Client> clients = new ArrayList<>();
        final BalanceProvider<ServerData> balanceProvider = new DefaultBalanceProvider(ZOOKEEPER_SERVER,SERVERS_PATH);

        try {
            for(int i = 0 ; i < CLIENT_QTY; i++){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Client client = new ClientImpl(balanceProvider);
                        clients.add(client);
                        try {
                            client.connect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                threadList.add(thread);
                thread.start();

                // 延迟3秒，模拟多次请求
                Thread.sleep(3000);
            }

            System.out.println("敲回车键退出！\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 关闭客户端
            for(int i = 0; i < clients.size(); i++){
                try {
                    clients.get(i).disConnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 关闭线程
            for (int i = 0 ; i < threadList.size(); i++){
                Thread thread = threadList.get(i);
                thread.interrupt();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

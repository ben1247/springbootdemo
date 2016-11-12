package com.shuyun.sbd.utils.zookeeper.zkclient.subscribe;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Component: 发布与订阅（服务注册和发现）客户端
 * Description:
 * Date: 16/11/11
 *
 * @author yue.zhang
 */
public class SubscribeZkClient {

    private static final int CLIENT_QRY = 5;

    private static final String ZOOKEEPER_SERVER = "127.0.0.1:2181";

    private static final String CONFIG_PATH = "/config";
    private static final String COMMAND_PATH = "/command";
    private static final String SERVERS_PATH = "/servers";

    public static void main(String [] args) throws Exception{
        List<ZkClient> clients = new ArrayList<>();
        List<WorkServer> workServers = new ArrayList<>();
        ManageServer manageServer = null;

        try {

            ServerConfig initConfig = new ServerConfig();
            initConfig.setDbUrl("jdbc:mysql://localhost:3306/mydb");
            initConfig.setDbUser("root");
            initConfig.setDbPwd("123456");

            ZkClient clientManage = new ZkClient(ZOOKEEPER_SERVER,5000,5000,new BytesPushThroughSerializer());
            manageServer = new ManageServer(SERVERS_PATH,COMMAND_PATH,CONFIG_PATH,clientManage,initConfig);
            manageServer.start();

            for(int i = 0 ; i < CLIENT_QRY; i++){
                ZkClient client = new ZkClient(ZOOKEEPER_SERVER,5000,5000,new BytesPushThroughSerializer());
                clients.add(client);

                // 服务器信息
                ServerData serverData = new ServerData();
                serverData.setId(i);
                serverData.setName("WorkServer#" + i);
                serverData.setAddress("192.168.1." + i);

                WorkServer workServer = new WorkServer(CONFIG_PATH,SERVERS_PATH,serverData,client,initConfig);
                workServers.add(workServer);
                workServer.start();
            }

            System.out.println("敲回车键退出！\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("Shutting down...");

            if(manageServer != null){
                try {
                    manageServer.stop();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            for(WorkServer workServer : workServers){
                try{
                    workServer.stop();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            for (ZkClient client : clients){
                try {
                    client.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

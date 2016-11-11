package com.shuyun.sbd.utils.zookeeper.zkclient.subscribe;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

/**
 * Component:
 * Description:
 * Date: 16/11/11
 *
 * @author yue.zhang
 */
public class WorkServer {

    private ZkClient zkClient;
    private String configPath; // zookeeper的 config 路径
    private String serversPath; // zookeeper的 server 路径
    private ServerData serverData;
    private ServerConfig serverConfig;
    private IZkDataListener dataListener;

    public WorkServer(String configPath,String serversPath,ServerData serverData,ZkClient zkClient,ServerConfig initConfig) {
        this.configPath = configPath;
        this.serversPath = serversPath;
        this.serverData = serverData;
        this.zkClient = zkClient;
        this.serverConfig = initConfig;

        this.dataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                String retJson = new String((byte[])data);
                ServerConfig serverConfigLocal = JSON.parseObject(retJson,ServerConfig.class);
                updateConfig(serverConfigLocal);
                System.out.println("new work server config is : " + serverConfig.toString());
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {

            }
        };
    }

    public void start(){
        System.out.println("work server start ...");
        initRunnding();
    }

    public void stop(){
        System.out.println("work server stop ...");
        zkClient.unsubscribeDataChanges(configPath,dataListener);
    }

    private void initRunnding(){
        registerMe();
        zkClient.subscribeDataChanges(configPath, dataListener);
    }

    private void registerMe(){
        String mePath = serversPath.concat("/").concat(serverData.getAddress());

        try {
            zkClient.createEphemeral(mePath, JSON.toJSONString(serverData).getBytes());
        }catch (ZkNoNodeException e){
            zkClient.createPersistent(serversPath,true);
            registerMe();
        }

    }

    private void updateConfig(ServerConfig serverConfig){
        this.serverConfig = serverConfig;
    }
}

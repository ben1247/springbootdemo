package com.shuyun.sbd.utils.zookeeper.curator.discovery.mydemo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;

/**
 * Component:
 * Description:
 * Date: 15/12/13
 *
 * @author yue.zhang
 */
public class MyConnectionStateListener implements ConnectionStateListener {

    private String zkRegPathPrefix;
    private String regContent;

    public MyConnectionStateListener(String zkRegPathPrefix, String regContent) {
        this.zkRegPathPrefix = zkRegPathPrefix;
        this.regContent = regContent;
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {

        if(newState == ConnectionState.LOST){
            System.out.println("掉线了");
            while (true) {
                try {
                    if(client.getZookeeperClient().blockUntilConnectedOrTimedOut()){
                        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(zkRegPathPrefix,regContent.getBytes());
                        System.out.println("链接上了");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


    }
}

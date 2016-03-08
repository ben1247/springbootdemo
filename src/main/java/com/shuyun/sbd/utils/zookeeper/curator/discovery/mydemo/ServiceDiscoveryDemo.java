package com.shuyun.sbd.utils.zookeeper.curator.discovery.mydemo;

import discovery.InstanceDetails;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.zookeeper.CreateMode;

/**
 * Component:
 * Description:
 * Date: 15/12/4
 *
 * @author yue.zhang
 */
public class ServiceDiscoveryDemo {

    public static void main(String[] args) throws Exception {

        String connectString = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString,retryPolicy);
        client.start();

        // 订单微服务
        String tradeServer = "127.0.0.1:8195";
        String zkRegPathPrefix = "/microservers/trades";

        // 监听
        MyConnectionStateListener listener = new MyConnectionStateListener(zkRegPathPrefix,tradeServer);
        client.getConnectionStateListenable().addListener(listener);

        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .forPath(zkRegPathPrefix, tradeServer.getBytes());


        client.close();
    }

}

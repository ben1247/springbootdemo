package com.shuyun.sbd.utils.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 15/11/26
 *
 * @author yue.zhang
 */
public class CuratorCrudExample {

    public static void main(String [] args) throws Exception {

        String zookeeperConnectionString = "127.0.0.1";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .namespace("curatordemo")
                .connectString(zookeeperConnectionString)
                .retryPolicy(retryPolicy)
                .build();

        client.start();

        client.create().withMode(CreateMode.EPHEMERAL).forPath("/example3", "example.basic".getBytes());



        CloseableUtils.closeQuietly(client);

    }

    /**
     * this will create the given ZNode with the given data
     * @param client
     * @param path
     * @param payload
     * @throws Exception
     */
    public static void create(CuratorFramework client, String path, byte[] payload) throws Exception {
        client.create().forPath(path, payload);
    }

    /**
     * this will create the given EPHEMERAL ZNode with the given data
     * @param client
     * @param path
     * @param payload
     * @throws Exception
     */
    public static void createEphemeral(CuratorFramework client, String path, byte[] payload) throws Exception {
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path, payload);
    }

    /**
     * this will create the given EPHEMERAL-SEQUENTIAL ZNode with the given
     * data using Curator protection.
     * @param client
     * @param path
     * @param payload
     * @throws Exception
     */
    public static void createEphemeralSequential(CuratorFramework client , String path , byte[] payload) throws Exception{
        client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path,payload);
    }

    /**
     * set data for the given node
     * @param client
     * @param path
     * @param payload
     * @throws Exception
     */
    public static void setData(CuratorFramework client, String path, byte[] payload) throws Exception {
        client.setData().forPath(path, payload);
    }

    /**
     * this is one method of getting event/async notifications
     * @param client
     * @param path
     * @param payload
     * @throws Exception
     */
    public static void setDataAsync(CuratorFramework client, String path, byte[] payload) throws Exception{
        // this is one method of getting event/async notifications
        CuratorListener listener = new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                // examine event for details
                System.out.println("setDataAsync listener event type : " + event.getType());
            }
        };
        client.getCuratorListenable().addListener(listener);
        // set data for the given node asynchronously. The completion
        // notification
        // is done via the CuratorListener.
        client.setData().inBackground().forPath(path,payload);
    }

    /**
     * this is another method of getting notification of an async completion
     * @param client
     * @param callback
     * @param path
     * @param payload
     * @throws Exception
     */
    public static void setDataAsyncWithCallback(CuratorFramework client, BackgroundCallback callback, String path, byte[] payload) throws Exception {
        client.setData().inBackground(callback).forPath(path, payload);
    }

    /**
     * delete the given node
     * @param client
     * @param path
     * @throws Exception
     */
    public static void delete(CuratorFramework client, String path) throws Exception {
        client.delete().forPath(path);
    }

    /**
     * delete the given node and guarantee that it completes
     * @param client
     * @param path
     * @throws Exception
     */
    public static void guaranteedDelete(CuratorFramework client, String path) throws Exception {

        client.delete().guaranteed().forPath(path);
    }


    /**
     * Get children and set a watcher on the node. The watcher notification
     * will come through the CuratorListener (see setDataAsync() above).
     * @param client
     * @param path
     * @return
     * @throws Exception
     */
    public static List<String> watchedGetChildren(CuratorFramework client, String path) throws Exception {
        return client.getChildren().watched().forPath(path);
    }

    /**
     * Get children and set the given watcher on the node.
     * @param client
     * @param path
     * @param watcher
     * @return
     * @throws Exception
     */
    public static List<String> watchedGetChildren(CuratorFramework client, String path, Watcher watcher) throws Exception {
        return client.getChildren().usingWatcher(watcher).forPath(path);
    }
}

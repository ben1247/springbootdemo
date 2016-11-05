package com.shuyun.sbd.utils.zookeeper.zkdemo;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Component: 同异步创建节点
 * Description:
 * Date: 16/10/31
 *
 * @author yue.zhang
 */
public class CreateNodeSync implements Watcher{

    private static ZooKeeper zooKeeper;

    public static void main(String [] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181",5000,new CreateNodeSync());
        System.out.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }

    private void doSomething(){
        try {
            String path = zooKeeper.create("/node_4","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(path);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("收到事件：" + event);
        if(event.getState() == Event.KeeperState.SyncConnected){
            doSomething();
        }
    }
}

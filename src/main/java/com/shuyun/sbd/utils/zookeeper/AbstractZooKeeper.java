package com.shuyun.sbd.utils.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Component:
 * Description:
 * Date: 15/11/19
 *
 * @author yue.zhang
 */
public class AbstractZooKeeper implements Watcher{

    private static final int SESSION_TIME = 2000;

    protected ZooKeeper zooKeeper;

    protected CountDownLatch countDownLatch = new CountDownLatch(1);

    public void connect(String hosts) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(hosts,SESSION_TIME,this);
        //等待所有工人完成工作
        countDownLatch.await();
        System.out.println("connect finish");
    }

    @Override
    public void process(WatchedEvent event) {
        if(event.getState() == Event.KeeperState.SyncConnected){
            System.out.println("============================== Do AbstractZooKeeper Process ================================");
            countDownLatch.countDown();
        }
    }

    public void close() throws InterruptedException {
        zooKeeper.close();
    }
}

package com.shuyun.sbd.utils.zookeeper.zkdemo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Component:
 * Description:
 * Date: 16/10/31
 *
 * @author yue.zhang
 */
public class CreateSession implements Watcher{

    private static ZooKeeper zooKeeper;

    public static void main(String [] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181",5000,new CreateSession());
        System.out.println(zooKeeper.getState());
        Thread.sleep(Integer.MAX_VALUE);
    }

    private void doSomething(){
        System.out.println("do something");
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("收到事件：" + event);
        if(event.getState() == Event.KeeperState.SyncConnected){
            doSomething();
        }
    }
}

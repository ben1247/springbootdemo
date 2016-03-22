package com.shuyun.sbd.utils.zookeeper.zkdemo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

/**
 * Component: 异步创建znode
 * Description:
 * Date: 16/3/22
 *
 * @author yue.zhang
 */
public class MasterAsyn implements Watcher{

    ZooKeeper zk;

    String hostPort;

    String serverId = Integer.toHexString(new Random().nextInt());

    boolean isLeader = false;

    AsyncCallback.StringCallback masterCreateCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case OK:
                    isLeader = true;
                    break;
                default:
                    isLeader = false;
            }
            System.out.println("I am " + (isLeader ? "" : " not") + " the leader  " + ctx.toString());
        }
    };

    AsyncCallback.DataCallback masterCheckCallback = new AsyncCallback.DataCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case NONODE:
                    runForMaster();
                    return;
            }
        }
    };


    MasterAsyn(String hostPort){
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort,15000,this);
    }

    void stopZK() throws InterruptedException {
        zk.close();
    }

    void checkMaster(){
        zk.getData("/master",false,masterCheckCallback,null);
    }

    void runForMaster(){
        zk.create("/master",serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,masterCreateCallback,"传进来咯");
    }


    @Override
    public void process(WatchedEvent event) {
        System.out.println("============================== do process start ==============================");
        System.out.println(event);
        System.out.println("============================== do process end ==============================");
    }

    public static void main(String[]args) throws IOException, InterruptedException {

        String hostPort = "127.0.0.1:2181";

        MasterAsyn m = new MasterAsyn(hostPort);

        m.startZK();

        m.runForMaster();

        Thread.sleep(60000);

        m.stopZK();
    }
}

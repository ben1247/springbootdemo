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

    final static String NODE_PATH = "/master";

    AsyncCallback.StringCallback masterCreateCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    checkMaster(); // 在链接丢失事件发生的情况下，客户端检查/master节点是否存在，因为客户端并不知道是否能够创建这个节点
                    break;
                case OK:
                    isLeader = true;
                    break;
                case NODEEXISTS:
                    masterExists(); // 如果存在则对该节点进行监控
                    break;
                default:
                    isLeader = false; // 如果发生了
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
                    break;
                case NONODE:
                    runForMaster();
                    break;
                default:
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
        zk.create(NODE_PATH,serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,masterCreateCallback,"传进来咯");
    }

    void masterExists(){
        zk.exists(NODE_PATH,masterExistsWatcher,masterExistsCallback,null);
    }

    Watcher masterExistsWatcher = new Watcher(){
        @Override
        public void process(WatchedEvent event) {
            if(event.getType()== Event.EventType.NodeDeleted){
                if(NODE_PATH.equals(event.getPath())){
                    runForMaster();
                }
            }
        }
    };

    AsyncCallback.StatCallback masterExistsCallback = new AsyncCallback.StatCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS: // 连接丢失的情况下重试
                    masterExists();
                    break;
                case OK: // 如果返回ok，判断znode节点是否存在，不存在就竞选主节点
                    if(stat == null){
//                        stat = MasterStates.RUNNING;
                        runForMaster();
                    }
                    break;
                default: // 如果发生意外情况，通过获取节点数据来检查/master是否存在
                    checkMaster();
            }
        }
    };



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

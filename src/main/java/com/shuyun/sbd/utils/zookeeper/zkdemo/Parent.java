package com.shuyun.sbd.utils.zookeeper.zkdemo;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Component: 业务场景：设置元数据
 * Description:
 * Date: 16/3/22
 *
 * @author yue.zhang
 */
public class Parent implements Watcher{

    private ZooKeeper zk;

    private String hostPort;

    public Parent(String hostPort){
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort,15000,this);
    }

    void stopZK() throws InterruptedException {
        zk.close();
    }

    public void bootstrap(){
        createParent("/workers",new byte[0]);
        createParent("/assign",new byte[0]);
        createParent("/tasks",new byte[0]);
        createParent("/status",new byte[0]);
    }

    public void createParent(String path , byte [] data){
        zk.create(path,data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,createParentCallback,data);
    }

    AsyncCallback.StringCallback createParentCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    createParent(path,(byte[])ctx);
                    break;
                case OK:
                    System.out.println("Parent created");
                    break;
                case NODEEXISTS:
                    System.out.println("Parent already registered: " + path);
                    break;
                default:
                    System.out.println("Something went wrong: " + KeeperException.create(KeeperException.Code.get(rc),path));
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

        Parent p = new Parent(hostPort);

        p.startZK();

        p.bootstrap();

        Thread.sleep(60000);

        p.stopZK();

    }
}

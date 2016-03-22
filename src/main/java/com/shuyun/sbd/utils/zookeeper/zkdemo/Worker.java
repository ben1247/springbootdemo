package com.shuyun.sbd.utils.zookeeper.zkdemo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;


/**
 * Component: 业务场景：注册从节点
 * Description:
 * Date: 16/3/22
 *
 * @author yue.zhang
 */
public class Worker implements Watcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    private ZooKeeper zk;

    private String hostPort;

    private String serverId = Integer.toHexString(new Random().nextInt());

    private String status;

    public Worker(String hostPort){
        this.hostPort = hostPort;
    }

    public void startZK() throws IOException {
        zk = new ZooKeeper(hostPort,15000,this);
    }

    public void stopZK() throws InterruptedException {
        zk.close();
    }

    @Override
    public void process(WatchedEvent event) {
        LOGGER.info(event.toString() + " , " + hostPort);
    }

    public void register(){
        zk.create("/workers/worker-"+serverId,
                "Idle".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                createWorkerCallBack,null);
    }

    AsyncCallback.StringCallback createWorkerCallBack = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    register();
                    break;
                case OK:
                    LOGGER.info("Registered successfully: " + serverId);
                    break;
                case NODEEXISTS:
                    LOGGER.warn("Already registered: " + serverId);
                default:
                    LOGGER.error("Something went wrong: " + KeeperException.create(KeeperException.Code.get(rc),path));
            }
        }
    };

    AsyncCallback.StatCallback statusUpdateCallback = new AsyncCallback.StatCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    updateStatus((String)ctx);
                    return;
            }
        }
    };

    synchronized private void updateStatus(String status){

        // 处理流程可能变得无序，因为zk对请求和响应都会很好地保持顺序，但如果链接丢失，我们又再发起一个新的请求，就会导致整个时序中出现空隙。
        // 因此，我们进行一个状态更新请求前，需要先获得当前状态，否则就要放弃更新，我们通过同步方式进行检查和重试操作。
        if(status.equals(this.status)){
            zk.setData("/workers/worker-"+serverId,status.getBytes(),-1,statusUpdateCallback,status);
        }
    }

    public void setStatus(String status){
        this.status = status; // 我们将状态信息保存到本地变量重，万一更新失败，我们需要重试
        updateStatus(status);
    }

    public static void main( String [] args) throws Exception{
        String hostPort = "127.0.0.1:2181";
        Worker w = new Worker(hostPort);
        w.startZK();
        w.register();

        w.setStatus("你是第一个工人");

        Thread.sleep(60000);
        w.stopZK();
    }
}

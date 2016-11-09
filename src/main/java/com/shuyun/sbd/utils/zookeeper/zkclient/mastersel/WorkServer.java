package com.shuyun.sbd.utils.zookeeper.zkclient.mastersel;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Component:
 * Description:
 * Date: 16/11/6
 *
 * @author yue.zhang
 */
public class WorkServer {

    // 记录服务器的状态
    private volatile boolean running = false;

    private ZkClient zkClient;

    private static final String MASTER_PATH = "/master";

    // 监听master节点的删除事件
    private IZkDataListener dataListener;

    // 当前服务器的基本信息
    private RunningData serverData;

    // 集群中master节点的基本信息
    private RunningData masterData;

    // 调度器
    private ScheduledExecutorService delayExector = Executors.newScheduledThreadPool(1);

    private int delayTime = 5;

    public WorkServer(RunningData rd){
        this.serverData = rd;
        this.dataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                // 当发现master节点被删除时

                // 如果上一轮master就是自己，则立刻去争抢master
                if(masterData != null && masterData.getName().equals(serverData.getName())){
                    takeMaster();
                }
                // 如果上一轮master不是自己，则延迟5秒钟再去争抢,这样就避免了不必要的开销
                else {
                    delayExector.schedule(new Runnable() {
                        @Override
                        public void run() {
                            takeMaster();
                        }
                    },delayTime, TimeUnit.SECONDS);
                }
            }
        };
    }

    // 服务器的启动函数
    public void start() throws Exception{
        // 如果服务器已经启动，则抛出已经启动的异常
        if(running){
            throw new Exception("server has startup...");
        }
        running = true;
        // 订阅master节点的删除事件
        zkClient.subscribeDataChanges(MASTER_PATH, dataListener);
        // 争抢master节点的权利
        takeMaster();
    }

    // 停止服务器
    public void stop() throws Exception{
        if(!running){
            throw new Exception("server has stoped...");
        }
        running = false;
        zkClient.unsubscribeDataChanges(MASTER_PATH, dataListener);
        releaseMaster();
    }

    private void takeMaster(){
        if(!running){
            return;
        }
        try {
            zkClient.create(MASTER_PATH,serverData, CreateMode.EPHEMERAL);
            masterData =  serverData;

            // 作为演示demo，我们这里每隔5秒就释放master
            delayExector.schedule(new Runnable() {
                @Override
                public void run() {
                    if(checkMaster()){
                        releaseMaster();
                    }
                }
            },5,TimeUnit.SECONDS);
        }catch (ZkNodeExistsException e){
            // 节点存在，则读取master节点
            RunningData runningData = zkClient.readData(MASTER_PATH,true);
            // 如果读取的master节点已经宕机，则重新争选
            if(runningData == null){
                takeMaster();
            }else {
                masterData = runningData;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void releaseMaster(){
        if(checkMaster()){
            zkClient.delete(MASTER_PATH);
        }
    }

    private boolean checkMaster(){
        try {
            masterData = zkClient.readData(MASTER_PATH);

            if(masterData.getName().equals(serverData.getName())){
                return true;
            }else {
                return false;
            }

        }catch (ZkNoNodeException e){
            return false;
        }catch (ZkInterruptedException e){
            // 中断异常，则进行重试
            return checkMaster();
        }catch (ZkException e){
            return false;
        }


    }


    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }
}

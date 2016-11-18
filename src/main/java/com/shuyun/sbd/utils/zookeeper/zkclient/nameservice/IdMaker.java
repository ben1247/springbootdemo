package com.shuyun.sbd.utils.zookeeper.zkclient.nameservice;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Component: 分布式的id生成器
 * Description:
 * Date: 16/11/17
 *
 * @author yue.zhang
 */
public class IdMaker {

    private ZkClient zkClient;

    private final String server; // 用于记录zookeeper的服务器地址

    private final String root; // 用来记录父节点路径

    private final String nodeName; // 顺序节点的名称

    private volatile boolean running = false; // 当前服务是否正在运行

    private ExecutorService cleanExecutor = null; // 线程池，用于删除zookeeper所创建的顺序节点

    public enum RemoveMethod{
        IMMEDIATELY , DELAY
    }

    public IdMaker(String server , String root, String nodeName) {
        this.server = server;
        this.root = root;
        this.nodeName = nodeName;
    }

    public void start() throws Exception{
        if(running){
            throw new Exception("server is running...");
        }
        running = true;
        init();
    }

    public void stop() throws Exception{
        if(!running){
            throw new Exception("server is stop...");
        }
        running = false;
        freeResource();
    }

    /**
     * 初始化服务器资源
     */
    private void init(){
        zkClient = new ZkClient(server,5000,5000,new BytesPushThroughSerializer());
        cleanExecutor = Executors.newFixedThreadPool(10);
        try {
            zkClient.createPersistent(root,true);
        }catch (ZkNodeExistsException e){
            // ignore
        }
    }

    /**
     * 释放服务器资源
     */
    private void freeResource(){

        if(cleanExecutor != null){
            cleanExecutor.shutdown();
            try {
                cleanExecutor.awaitTermination(2, TimeUnit.SECONDS);
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                cleanExecutor = null;
            }
        }

        if (zkClient != null){
            zkClient.close();
            zkClient = null;
        }
    }

    /**
     * 检测当前服务是否正在运行
     */
    private void checkRunning() throws Exception {
        if (!running){
            throw new Exception("请先调用start");
        }
    }

    /**
     * 生成分布式唯一ID
     * @param removeMethod 删除顺序节点的策略
     * @return
     * @throws Exception
     */
    public String generateId(RemoveMethod removeMethod) throws Exception{

        checkRunning();

        final String fullNodePath = root.concat("/").concat(nodeName);
        final String ourPath = zkClient.createPersistentSequential(fullNodePath,null);

        // 由于创建出来的顺序节点在zookeeper里没有多大意义，所以每次都需要删除
        if(removeMethod.equals(RemoveMethod.IMMEDIATELY)){ // 立即删除
            zkClient.delete(ourPath);
        }else if(removeMethod.equals(RemoveMethod.DELAY)){ // 延迟删除，则使用线程池的方式
            cleanExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    zkClient.delete(ourPath);
                }
            });
        }

        // node-0000001、node-0000002
        return extractId(ourPath);
    }

    private String extractId(String str){
        int index = str.lastIndexOf(nodeName);
        if(index >= 0){
            index += nodeName.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;

    }
}


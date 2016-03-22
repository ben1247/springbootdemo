package com.shuyun.sbd.utils.zookeeper.zkdemo;

import org.apache.zookeeper.*;

/**
 * Component: 业务场景：任务队列化
 * Description: 使用有序节点，这样做有2个好处
 * 第一，序列号指定了任务被队列化的顺序
 * 第二，可以通过很少的工作为任务创建基于序列号的唯一路径。
 * Date: 16/3/22
 *
 * @author yue.zhang
 */
public class Client implements Watcher{

    private ZooKeeper zk;

    private String hostPort;

    public Client(String hostPort){
        this.hostPort = hostPort;
    }

    public void startZK()throws Exception{
        zk = new ZooKeeper(hostPort,15000,this);
    }

    public void stopZK() throws InterruptedException {
        zk.close();
    }

    public String queueCommand(String command)throws Exception{
        try{
            String name = zk.create("/tasks/task-",command.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT_SEQUENTIAL);

            // 我们无法确定使用CreateMode.EPHEMERAL_SEQUENTIAL调用create的序列号，create方法会返回新建节点的名称
            return name;
        }catch (KeeperException.NodeExistsException e){
            throw new Exception(command + " already appears to be running");
        }catch (KeeperException.ConnectionLossException e){
        }
        return null;
    }


    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    public static void main(String[]args)throws Exception{
        String hostPort =  "127.0.0.1:2181";
        Client c = new Client(hostPort);
        c.startZK();

        String name = c.queueCommand("cmd");

        System.out.print("Created: " + name);

        c.stopZK();
    }
}

package com.shuyun.sbd.utils.zookeeper.zkdemo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Date;

/**
 * Component: 业务场景：管理客户端
 * Description:
 * Date: 16/3/22
 *
 * @author yue.zhang
 */
public class AdminClient implements Watcher {

    private ZooKeeper zk;

    private String hostPort;

    public AdminClient(String hostPort){
        this.hostPort = hostPort;
    }
    
    public void startZK()throws Exception{
        zk = new ZooKeeper(hostPort,15000,this);
    }

    public void stopZK() throws InterruptedException {
        zk.close();
    }

    public void listState() throws KeeperException, InterruptedException {
        try{
            Stat stat = new Stat();
            byte [] masterData = zk.getData("/master",false,stat);
            Date startDate = new Date(stat.getCtime());
            System.out.println("Master: " + new String(masterData) + " since " + startDate);
        }catch (KeeperException.NoNodeException e){
            System.out.println("No Master");
        }

        System.out.println("Workers: ");
        for(String w : zk.getChildren("/workers",false)){
            byte [] data = zk.getData("/workers/" + w,false,null);
            String state = new String(data);
            System.out.println("\t" + w + " : " + state);
        }

        System.out.println("Tasks: ");
        for(String t : zk.getChildren("/assign",false)){
            System.out.println("\t" + t);
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    public static void main(String [] args)throws Exception{
        String hortPost = "127.0.0.1:2181";

        AdminClient c = new AdminClient(hortPost);
        c.startZK();

        c.listState();

        c.stopZK();
    }

}

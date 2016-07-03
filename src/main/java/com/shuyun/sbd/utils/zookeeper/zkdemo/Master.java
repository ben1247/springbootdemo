package com.shuyun.sbd.utils.zookeeper.zkdemo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

/**
 * Component: 同步创建znode
 * Description:
 * Date: 16/3/22
 *
 * @author yue.zhang
 */
public class Master implements Watcher {

    ZooKeeper zk;

    String hostPort;

    String serverId = Integer.toHexString(new Random().nextInt());

    boolean isLeader = false;

    Master(String hostPort){
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort,15000,this);
    }

    void stopZK() throws InterruptedException {
        zk.close();
    }

    boolean checkMaster(){
        while (true){
            try {
                Stat stat = new Stat();
                byte [] data = zk.getData("/master",false,stat);
                isLeader = new String(data).equals(serverId);
                return true;
            } catch (KeeperException.NoNodeException e) {
                e.printStackTrace();
                return false;
            } catch (KeeperException.ConnectionLossException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    void runForMaster()  {

        while (true){
            try {
                zk.create("/master",serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                isLeader = true;
                break;
            } catch (KeeperException.NodeExistsException e) {
                isLeader = false;
                break;
            } catch (KeeperException.ConnectionLossException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }

            if(checkMaster()){
                break;
            }
        }

    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("============================== do process start ==============================");
        System.out.println(event);
        System.out.println("============================== do process end ==============================");
    }

    public static void main(String[]args) throws IOException, InterruptedException {

        String hostPort = "127.0.0.1:2181";

        Master m = new Master(hostPort);

        m.startZK();

        m.runForMaster();

        if(m.isLeader){
            System.out.println("i am leader");
            Thread.sleep(60000);
        }else{
            System.out.println("Someone else is the leader");
        }

        m.stopZK();
    }
}

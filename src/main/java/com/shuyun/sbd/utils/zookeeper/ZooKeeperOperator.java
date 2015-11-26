package com.shuyun.sbd.utils.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.util.Arrays;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 15/11/19
 *
 * @author yue.zhang
 */
public class ZooKeeperOperator extends AbstractZooKeeper{

    public void create(String path,byte [] data) throws KeeperException, InterruptedException {
        this.zooKeeper.create(path,data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void getChild(String path) throws KeeperException, InterruptedException {
        List<String> datas = this.zooKeeper.getChildren(path,false);
        if(datas.isEmpty()){
            System.out.println("没有子节点");
        }else{
            for(String data: datas){
                System.out.println(data);
            }
        }
    }

    public byte[] getData(String path) throws KeeperException, InterruptedException {
        return this.zooKeeper.getData(path,false,null);
    }

    public static void main(String [] args){
        try{
            ZooKeeperOperator operator = new ZooKeeperOperator();
            operator.connect("127.0.0.1");

//            operator.create("/root",null);
//            System.out.println(Arrays.toString(operator.getData("/root"),"utf-8"));
//
//            byte[] data = new byte[]{'a','b','c','d'};
//            operator.create("/root/child1",data);
//            System.out.println(Arrays.toString(operator.getData("/root/child1"),"utf-8"));
//
//            operator.create("/root/child2",data);
//            System.out.println(Arrays.toString(operator.getData("/root/child2"),"utf-8"));
//
//            String zktest="ZooKeeper的Java API测试";
//            operator.create("/root/child3", zktest.getBytes("utf-8"));

            System.out.println("获取设置的信息：" + new String(operator.getData("/root/child3"),"utf-8"));

            System.out.println("节点孩子信息:");
            operator.getChild("/root");

            operator.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

package com.shuyun.sbd.utils.zookeeper;

import org.apache.zookeeper.*;

/**
 * Component:
 * Description:
 * Date: 15/11/12
 *
 * @author yue.zhang
 */
public class ZookeeperClient {

    public static void main(String [] args) throws Exception{

        String rootName = "testRootPath";


        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 500000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(String.format("已经触发了%s事件",event.getType()));
            }
        });

        // 创建节点
        zk.create("/"+rootName,rootName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 创建子节点
        zk.create("/"+rootName+"/testChildPathOne","testChildPathOne".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);

        // 显示节点数据
        System.out.println("====================== " + new String(zk.getData("/" + rootName,true,null)));

        // 取出子目录节点列表
        System.out.println("====================== " + zk.getChildren("/" + rootName,true));

        // 修改子目录节点数据
        zk.setData("/"+rootName + "/testChildPathOne" , "modifyTestChildPathOne".getBytes(),-1);

        System.out.println("====================== 目前节点状态[ " + zk.exists("/" + rootName,true) + " ]");

        // 创建另一个子目录节点
        zk.create("/" + rootName + "/testChildPathTwo" , "testChildPathTwo".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.EPHEMERAL);

        System.out.println(new String(zk.getData("/"+rootName+"/testChildPathTwo",true,null)));

        // 删除子目录节点
        zk.delete("/" + rootName + "/testChildPathOne",-1);
        zk.delete("/" + rootName + "/testChildPathTwo",-1);

        // 删除父节点
        zk.delete("/" + rootName , -1);

        // 关闭链接
        zk.close();

    }

}

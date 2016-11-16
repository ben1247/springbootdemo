package com.shuyun.sbd.utils.zookeeper.zkdemo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

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


        ZooKeeper zk = new ZooKeeper("127.0.0.1", 500000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(String.format("已经触发了%s事件",event.getType()));
            }
        });

        /* 出于安全考虑，服务器会为会话ID创建一个密码，ZooKeeper服务器可以校验这个密码。
         这个密码将在创建会话时与会话ID一同发送给客户端。
         与新的服务器重新建立会话的时候，客户端会和会话ID一同发送这个密码。*/
        String sessionPasswd = new String(zk.getSessionPasswd());

        // 创建节点
        zk.create("/"+rootName,rootName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 创建子节点
        zk.create("/"+rootName+"/testChildPathOne","testChildPathOne".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);

        // 显示节点数据 ， 参数stat：数据的版本等信息可以通过 stat 来指定
        System.out.println("====================== " + new String(zk.getData("/" + rootName,true,null)));

        // 取出子目录节点列表
        System.out.println("====================== " + zk.getChildren("/" + rootName,true));

        // 修改子目录节点数据,version 为 -1 可以匹配任何版本，也就修改了这个目录节点所有数据
        Stat stat = zk.setData("/"+rootName + "/testChildPathOne" , "modifyTestChildPathOne".getBytes(),-1);
        System.out.println(String.format("====================== 第一次修改后 czxid：%s , mzxid：%s , pzxid：%s",stat.getCzxid(),stat.getMzxid(),stat.getPzxid()));

        stat = zk.setData("/"+rootName + "/testChildPathOne" , "modifyTestChildPathOne2".getBytes(),-1);
        System.out.println(String.format("====================== 第二次修改后 czxid：%s , mzxid：%s , pzxid：%s",stat.getCzxid(),stat.getMzxid(),stat.getPzxid()));

        System.out.println("====================== 目前节点状态[ " + zk.exists("/" + rootName,true) + " ]");

        // 创建另一个子目录节点
        zk.create("/" + rootName + "/testChildPathTwo" , "testChildPathTwo".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.EPHEMERAL);

        // 参数stat：数据的版本等信息可以通过 stat 来指定
        System.out.println(new String(zk.getData("/"+rootName+"/testChildPathTwo",true,null)));

        // 删除子目录节点,version 为 -1 可以匹配任何版本，也就删除了这个目录节点所有数据
        zk.delete("/" + rootName + "/testChildPathOne",-1);
        zk.delete("/" + rootName + "/testChildPathTwo",-1);

        // 删除父节点
        zk.delete("/" + rootName , -1);

        // 关闭链接
        zk.close();

    }

}

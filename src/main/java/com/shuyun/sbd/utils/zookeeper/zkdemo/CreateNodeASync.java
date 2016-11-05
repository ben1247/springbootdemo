package com.shuyun.sbd.utils.zookeeper.zkdemo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Component: 异步创建节点
 * Description:
 * Date: 16/10/31
 *
 * @author yue.zhang
 */
public class CreateNodeASync implements Watcher{

    private static ZooKeeper zooKeeper;

    public static void main(String [] args) throws IOException, InterruptedException, NoSuchAlgorithmException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181",5000,new CreateNodeASync());
        System.out.println(zooKeeper.getState());

        //
        System.out.println(DigestAuthenticationProvider.generateDigest("zhangyue:123456"));

        Thread.sleep(Integer.MAX_VALUE);
    }

    private void doSomething(){


        try {
            // 权限 , 如果不是在这个ip上访问节点的话，可以使用  addauth digest zhangyue:123456 ，这样就能访问了
            ACL aclIp = new ACL(ZooDefs.Perms.READ,new Id("ip","172.18.53.205"));
            ACL aclDigest = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE,new Id("digest", DigestAuthenticationProvider.generateDigest("zhangyue:123456")));
            ArrayList<ACL> acls = new ArrayList<>();
            acls.add(aclDigest);
            acls.add(aclIp);


            zooKeeper.create("/node_5", "123".getBytes(), ZooDefs.Ids.READ_ACL_UNSAFE, CreateMode.PERSISTENT, new IStringCallback(), "创建");

            zooKeeper.create("/node_8", "123".getBytes(), acls, CreateMode.PERSISTENT, new IStringCallback(), "创建");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("收到事件：" + event);
        if(event.getState() == Event.KeeperState.SyncConnected){
            doSomething();
        }
    }

    static class IStringCallback implements AsyncCallback.StringCallback{

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            StringBuilder sb = new StringBuilder();
            sb.append("rc=" + rc).append("\n");
            sb.append("path=" + path).append("\n");
            sb.append("ctx=" + ctx).append("\n");
            sb.append("name=" + name).append("\n");
            System.out.println(sb.toString());
        }
    }
}

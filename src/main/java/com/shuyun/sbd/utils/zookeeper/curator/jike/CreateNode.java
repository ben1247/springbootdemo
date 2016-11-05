package com.shuyun.sbd.utils.zookeeper.curator.jike;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;

/**
 * Component:
 * Description:
 * Date: 16/11/1
 *
 * @author yue.zhang
 */
public class CreateNode {


    public static void main(String [] args) throws Exception {

        // 重试次数为三次，第一次间隔1秒，之后递增
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);

        // 最多重试5次，每次重试间隔1秒钟
//        RetryPolicy retryPolicy = new RetryNTimes(5,1000);

        // 整个重试时间不能超过5秒，每次重试间隔1秒钟
        RetryPolicy retryPolicy = new RetryUntilElapsed(5000,1000);


        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 5000, 5000, retryPolicy);

//        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(5000).connectionTimeoutMs(5000).retryPolicy(retryPolicy).build();
        
        client.start();

        // 权限 , 如果不是在这个ip上访问节点的话，可以使用  addauth digest zhangyue:123456 ，这样就能访问了
        ACL aclIp = new ACL(ZooDefs.Perms.READ,new Id("ip","172.18.53.205"));
        ACL aclDigest = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE,new Id("digest", DigestAuthenticationProvider.generateDigest("zhangyue:123456")));
        ArrayList<ACL> acls = new ArrayList<>();
        acls.add(aclDigest);
        acls.add(aclIp);

        String path = client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .withACL(acls)
                .forPath("/curator_1/1", "123".getBytes());

        System.out.println(path);

        Thread.sleep(Integer.MAX_VALUE);
    }

}

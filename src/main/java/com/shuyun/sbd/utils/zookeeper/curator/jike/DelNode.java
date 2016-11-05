package com.shuyun.sbd.utils.zookeeper.curator.jike;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

/**
 * Component:
 * Description:
 * Date: 16/11/1
 *
 * @author yue.zhang
 */
public class DelNode {


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

        // guaranteed() 这个方法会在此操作由于某种原因未能及时删除的情况下进行后台不断的删除，直到成功为止
        client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(-1).forPath("/curator_1");

        Thread.sleep(Integer.MAX_VALUE);
    }

}

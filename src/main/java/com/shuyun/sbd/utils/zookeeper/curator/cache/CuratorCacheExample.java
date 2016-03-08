package com.shuyun.sbd.utils.zookeeper.curator.cache;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 15/12/2
 *
 * @author yue.zhang
 */
public class CuratorCacheExample {

    public static void main(String [] args) throws Exception {
        CuratorCacheExample example = new CuratorCacheExample();
        example.pathCache();
    }

    public void pathCache() throws Exception{
        String connect = "127.0.0.1";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        CuratorFramework client = CuratorFrameworkFactory.builder().namespace("curatordemo").connectString(connect).retryPolicy(retryPolicy).build();

        client.start();

        client.create().withMode(CreateMode.EPHEMERAL).forPath("/example3", "example.basic".getBytes());

        PathChildrenCache cache = new PathChildrenCache(client,"/example3",true);

        cache.start();


        List<ChildData> childDatas = cache.getCurrentData();
        System.out.println(childDatas.size());


        client.close();
        cache.close();
    }

}

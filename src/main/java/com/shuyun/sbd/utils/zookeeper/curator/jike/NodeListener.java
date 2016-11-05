package com.shuyun.sbd.utils.zookeeper.curator.jike;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

/**
 * Component: 节点监听
 * Description:
 * Date: 16/11/1
 *
 * @author yue.zhang
 */
public class NodeListener {


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

        final NodeCache cache = new NodeCache(client,"/curator_1");
        cache.start();
        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                byte [] ret = cache.getCurrentData().getData();
                System.out.println("new data: " + new String(ret));
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
    }

}

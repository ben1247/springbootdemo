package com.shuyun.sbd.utils.zookeeper.curator.jike;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryUntilElapsed;

/**
 * Component: 节点监听
 * Description:
 * Date: 16/11/1
 *
 * @author yue.zhang
 */
public class NodeChildrenListener {


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

        final PathChildrenCache cache = new PathChildrenCache(client,"/curator_1",true);
        cache.start();
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED: " + event.getData());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED: " + event.getData());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED: " + event.getData());
                        break;
                    default:
                        System.out.println("default: " + event.getData());
                        break;
                }
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
    }

}

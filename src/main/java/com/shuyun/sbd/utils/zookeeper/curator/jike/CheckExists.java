package com.shuyun.sbd.utils.zookeeper.curator.jike;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Component:
 * Description:
 * Date: 16/11/1
 *
 * @author yue.zhang
 */
public class CheckExists {


    public static void main(String [] args) throws Exception {

        ExecutorService es = Executors.newFixedThreadPool(5);

        // 重试次数为三次，第一次间隔1秒，之后递增
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);

        // 最多重试5次，每次重试间隔1秒钟
//        RetryPolicy retryPolicy = new RetryNTimes(5,1000);

        // 整个重试时间不能超过5秒，每次重试间隔1秒钟
        RetryPolicy retryPolicy = new RetryUntilElapsed(5000,1000);


        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 5000, 5000, retryPolicy);

//        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(5000).connectionTimeoutMs(5000).retryPolicy(retryPolicy).build();

        client.start();

//        Stat s = client.checkExists().forPath("/curator_1"); // 同步调用

        // 异步调用
        client.checkExists().inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                CuratorEventType t = event.getType();

                int r = event.getResultCode(); // 返回码

                Object o = event.getContext(); // 上下文 , 这里是 123

                event.getPath();

                event.getChildren();

                event.getData();

                Stat stat = event.getStat();
                System.out.println(stat);

            }
        },"123",es).forPath("/curator_1");

        Thread.sleep(Integer.MAX_VALUE);
    }

}

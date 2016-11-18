package com.shuyun.sbd.utils.zookeeper.zkclient.queue;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Component:
 * Description:
 * Date: 16/11/17
 *
 * @author yue.zhang
 */
public class TestDistributedBlockingQueue {

    public static void main(String [] args){

        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000, 5000, new SerializableSerializer());
        String root = "/Queue";

        final DistributedBlockingQueue<User> queue = new DistributedBlockingQueue<>(zkClient,root);

        User user1 = new User();
        user1.setId("1");
        user1.setName("xiao zhang");

        User user2 = new User();
        user2.setId("2");
        user2.setName("xiao liu");

        ScheduledExecutorService delayExector = Executors.newScheduledThreadPool(1);
        int delayTime = 5;
        try {

            // 延迟5秒再放入数据
            delayExector.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        queue.offer(user1);
                        queue.offer(user2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },delayTime, TimeUnit.SECONDS);

            System.out.println("ready poll!");
            User u1 = queue.poll();
            System.out.println("get u1 " + u1.getName());
            User u2 = queue.poll();
            System.out.println("get u2 " + u2.getName());

            if (user1.getId().equals(u1.getId()) && user2.getId().equals(u2.getId())){
                System.out.println("Success!");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            delayExector.shutdown();
            try {
                delayExector.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

package com.shuyun.sbd.utils.zookeeper.zkclient.queue;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * Component:
 * Description:
 * Date: 16/11/17
 *
 * @author yue.zhang
 */
public class TestDistributedSimpleQueue {

    public static void main(String [] args){

        ZkClient zkClient = new ZkClient("127.0.0.1:2181",5000,5000,new SerializableSerializer());
        String root = "/Queue";

        final DistributedSimpleQueue<User> queue = new DistributedBlockingQueue<>(zkClient,root);

        User user1 = new User();
        user1.setId("1");
        user1.setName("xiao zhang");

        User user2 = new User();
        user2.setId("2");
        user2.setName("xiao liu");

        try {
            queue.offer(user1);
            queue.offer(user2);

            User u1 = queue.poll();
            User u2 = queue.poll();

            if (user1.getId().equals(u1.getId()) && user2.getId().equals(u2.getId())){
                System.out.println("Success!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

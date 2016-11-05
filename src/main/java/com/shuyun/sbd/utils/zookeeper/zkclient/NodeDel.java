package com.shuyun.sbd.utils.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * Component:
 * Description:
 * Date: 16/11/1
 *
 * @author yue.zhang
 */
public class NodeDel {

    public static void main(String [] args){
        ZkClient zc = new ZkClient("127.0.0.1:2181",10000,10000,new SerializableSerializer());
        System.out.println("connected ok!");

        boolean exists1 = zc.delete("/zkclient_1");
        boolean exists2 = zc.deleteRecursive("/zkclient_1");
        System.out.println(exists1);
        System.out.println(exists2);
    }

}

package com.shuyun.sbd.utils.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/11/1
 *
 * @author yue.zhang
 */
public class NodeExists {

    public static void main(String [] args){
        ZkClient zc = new ZkClient("127.0.0.1:2181",10000,10000,new SerializableSerializer());
        System.out.println("connected ok!");
        boolean exists = zc.exists("/zkclient_1");
        System.out.println(exists);
    }

}

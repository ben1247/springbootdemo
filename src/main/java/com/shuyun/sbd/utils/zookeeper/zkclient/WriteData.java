package com.shuyun.sbd.utils.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

/**
 * Component:
 * Description:
 * Date: 16/11/1
 *
 * @author yue.zhang
 */
public class WriteData {

    public static void main(String [] args){
        ZkClient zc = new ZkClient("127.0.0.1:2181",10000,10000,new SerializableSerializer());
        System.out.println("connected ok!");

        zc.writeData("/zkclient_1",new User(2,"test2"),1);


    }

}

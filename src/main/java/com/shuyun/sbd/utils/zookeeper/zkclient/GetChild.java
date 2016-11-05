package com.shuyun.sbd.utils.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/11/1
 *
 * @author yue.zhang
 */
public class GetChild {

    public static void main(String [] args){
        ZkClient zc = new ZkClient("127.0.0.1:2181",10000,10000,new SerializableSerializer());
        System.out.println("connected ok!");

        List<String> children = zc.getChildren("/zkclient_1");
        for(String child : children){

            System.out.println("create path: " + child);
        }


    }

}

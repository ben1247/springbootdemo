package com.shuyun.sbd.utils.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * Component:
 * Description:
 * Date: 16/11/1
 *
 * @author yue.zhang
 */
public class GetData {

    public static void main(String [] args){
        ZkClient zc = new ZkClient("127.0.0.1:2181",10000,10000,new SerializableSerializer());
        System.out.println("connected ok!");

//        zc.addAuthInfo();  添加权限

        Stat stat = new Stat();
        User user = zc.readData("/zkclient_1",stat);
        System.out.println(user.toString());
        System.out.println(stat);

        
    }

}

package com.shuyun.sbd.utils.zookeeper.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
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
public class SubscribeChildChanges {

    private static class ZkChildListener implements IZkChildListener{

        @Override
        public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
            System.out.println("通知来了！！！！！！   " + parentPath);
            System.out.println("通知来了！！！！！！   " + currentChilds.toString());
        }
    }

    public static void main(String [] args) throws InterruptedException {
        ZkClient zc = new ZkClient("127.0.0.1:2181",10000,10000,new SerializableSerializer());
        System.out.println("connected ok!");

        zc.subscribeChildChanges("/zkclient_1", new ZkChildListener());

        Thread.sleep(Integer.MAX_VALUE);
    }

}

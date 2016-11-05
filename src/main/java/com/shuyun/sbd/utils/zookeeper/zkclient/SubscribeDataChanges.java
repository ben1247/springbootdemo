package com.shuyun.sbd.utils.zookeeper.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/11/1
 *
 * @author yue.zhang
 */
public class SubscribeDataChanges {

    private static class ZkDataListener implements IZkDataListener{

        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {
            System.out.println("handleDataChange 通知来了！！！！！！   " + dataPath + "    " + data.toString());
        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {
            System.out.println("handleDataDeleted 通知来了！！！！！！   " + dataPath);
        }
    }

    public static void main(String [] args) throws InterruptedException {
        ZkClient zc = new ZkClient("127.0.0.1:2181",10000,10000,new BytesPushThroughSerializer());
        System.out.println("connected ok!");

        zc.subscribeDataChanges("/zkclient_1", new ZkDataListener());

        Thread.sleep(Integer.MAX_VALUE);
    }

}

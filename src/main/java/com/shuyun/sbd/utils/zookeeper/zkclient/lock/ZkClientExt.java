package com.shuyun.sbd.utils.zookeeper.zkclient.lock;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.Callable;

/**
 * Component:
 * Description:
 * Date: 16/11/14
 *
 * @author yue.zhang
 */
public class ZkClientExt extends ZkClient{

    public ZkClientExt(String zkServers , int sessionTimeOut, int connectionTimeOut, ZkSerializer zkSerializer){
        super(zkServers,sessionTimeOut,connectionTimeOut,zkSerializer);
    }

    @Override
    public void watchForData(String path) {
        retryUntilConnected(new Callable<Object>() {
            public Object call() throws Exception{
                Stat stat = new Stat();
                _connection.readData(path,stat,true);
                return null;
            }
        });
    }
}

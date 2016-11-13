package com.shuyun.sbd.utils.zookeeper.zkclient.balance.client;

import com.shuyun.sbd.utils.zookeeper.zkclient.balance.server.ServerData;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/11/13
 *
 * @author yue.zhang
 */
public class DefaultBalanceProvider extends AbstractBalanceProvider<ServerData> {

    private final String zkServer; // zk服务器的地址
    private final String serversPath; // zk中server节点的路径
    private final ZkClient zkClient; // zk客户端

    private static final Integer SESSION_TIME_OUT = 10000;
    private static final Integer CONNECT_TIME_OUT = 10000;

    public DefaultBalanceProvider(String zkServer, String serversPath){
        this.zkServer = zkServer;
        this.serversPath = serversPath;
        this.zkClient = new ZkClient(zkServer,SESSION_TIME_OUT,CONNECT_TIME_OUT,new SerializableSerializer());
    }

    @Override
    protected ServerData balanceAlgorithm(List<ServerData> items) {
        if(items.size() > 0){
            Collections.sort(items);
            return items.get(0);
        }else{
            return null;
        }
    }

    @Override
    protected List<ServerData> getBalanceItems() {

        List<ServerData> servers = new ArrayList<>();
        List<String> children = zkClient.getChildren(serversPath);
        for(int i = 0 ; i < children.size(); i++){
            ServerData server = zkClient.readData(serversPath + "/" + children.get(i));
            servers.add(server);
        }

        return servers;
    }
}

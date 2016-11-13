package com.shuyun.sbd.utils.zookeeper.zkclient.balance.server;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

/**
 * Component:
 * Description:
 * Date: 16/11/13
 *
 * @author yue.zhang
 */
public class DefaultRegistProvider implements RegistProvider {

    @Override
    public void regist(Object context) throws Exception {
        // 1. path 需要知道父节点路径
        // 2. zkClient
        // 3. serverData
        ZooKeeperRegistContext registContext = (ZooKeeperRegistContext)context;
        String path = registContext.getPath();
        ZkClient zkClient = registContext.getZkClient();

        try {
            zkClient.createEphemeral(path,registContext.getData());
        }catch (ZkNoNodeException e){
            // 父节点不存在
            String parentDir = path.substring(0,path.lastIndexOf("/"));
            zkClient.createPersistent(parentDir,true);
            regist(registContext);
        }

    }

    @Override
    public void unRegist(Object context) throws Exception {

    }
}

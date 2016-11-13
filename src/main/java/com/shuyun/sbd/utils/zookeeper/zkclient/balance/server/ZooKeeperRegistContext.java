package com.shuyun.sbd.utils.zookeeper.zkclient.balance.server;

import org.I0Itec.zkclient.ZkClient;

/**
 * Component: zookeeper 注册的上下文
 * Description:
 * Date: 16/11/13
 *
 * @author yue.zhang
 */
public class ZooKeeperRegistContext {

    private String path;

    private ZkClient zkClient;

    private Object data;

    public ZooKeeperRegistContext(String path, ZkClient zkClient, Object data) {
        this.path = path;
        this.zkClient = zkClient;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }
}

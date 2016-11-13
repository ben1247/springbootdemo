package com.shuyun.sbd.utils.zookeeper.zkclient.balance.client;

/**
 * Component:
 * Description:
 * Date: 16/11/13
 *
 * @author yue.zhang
 */
public interface Client {

    // 与服务器链接
    void connect() throws Exception;

    // 与服务器断开链接
    void disConnect() throws Exception;

}

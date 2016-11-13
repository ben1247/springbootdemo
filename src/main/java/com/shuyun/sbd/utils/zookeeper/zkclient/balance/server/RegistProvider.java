package com.shuyun.sbd.utils.zookeeper.zkclient.balance.server;

/**
 * Component:
 * Description:
 * Date: 16/11/12
 *
 * @author yue.zhang
 */
public interface RegistProvider {

    void regist(Object context) throws Exception;

    void unRegist(Object context) throws Exception;

}

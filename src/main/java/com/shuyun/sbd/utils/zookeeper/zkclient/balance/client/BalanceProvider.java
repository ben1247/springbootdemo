package com.shuyun.sbd.utils.zookeeper.zkclient.balance.client;

/**
 * Component:
 * Description:
 * Date: 16/11/13
 *
 * @author yue.zhang
 */
public interface BalanceProvider<T> {

    T getBalanceItem();

}

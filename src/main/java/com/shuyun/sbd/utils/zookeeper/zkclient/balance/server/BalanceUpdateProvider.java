package com.shuyun.sbd.utils.zookeeper.zkclient.balance.server;

/**
 * Component: 负载更新
 * Description:
 * Date: 16/11/12
 *
 * @author yue.zhang
 */
public interface BalanceUpdateProvider {

    // 增加负载
    boolean addBalance(Integer step);

    // 减少负载
    boolean reduceBalance(Integer step);


}

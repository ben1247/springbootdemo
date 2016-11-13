package com.shuyun.sbd.utils.zookeeper.zkclient.balance.client;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/11/13
 *
 * @author yue.zhang
 */
public abstract class AbstractBalanceProvider<T> implements BalanceProvider<T> {

    /**
     * 从列表中根据负载均衡算法得到一个项
     * @param items
     * @return
     */
    protected abstract T balanceAlgorithm(List<T> items);

    /**
     * 获取所有负载均衡项
     * @return
     */
    protected abstract List<T> getBalanceItems();

    public T getBalanceItem(){
        return balanceAlgorithm(getBalanceItems());
    }

}

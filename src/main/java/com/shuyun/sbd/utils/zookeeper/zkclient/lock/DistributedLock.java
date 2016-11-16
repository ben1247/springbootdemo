package com.shuyun.sbd.utils.zookeeper.zkclient.lock;

import java.util.concurrent.TimeUnit;

/**
 * Component: 分布式锁接口
 * Description:
 * Date: 16/11/14
 *
 * @author yue.zhang
 */
public interface DistributedLock {

    /**
     * 获取锁，如果没有得到就一直等待
     * @throws Exception
     */
    void acquire() throws Exception;

    /**
     * 获取锁，直到超时
     * @param time
     * @param unit
     * @return
     * @throws Exception
     */
    boolean acquire(long time , TimeUnit unit) throws Exception;

    /**
     * 释放锁
     * @throws Exception
     */
    void release() throws Exception;

}

package com.shuyun.sbd.utils.lock.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Component: Semaphore 信号量 demo
 * Description:
 * 一个对象池实例，对象池最大容量100。因此，当同时有超过100个对象请求时，对象池就出现了资源短缺，未能获得资源的线程就需要等待。
 * Date: 17/1/22
 *
 * @author yue.zhang
 */
public class Pool {

    private static final int MAX_AVAILABLE = 200;
    // 最大可以有100个许可
    private final Semaphore available = new Semaphore(MAX_AVAILABLE,true);

    // 获得一个池内的对象
    public Object getItem() throws InterruptedException {
        // 申请一个许可,同时只能有100个线程进入取得，可用项超过100个需要等待。
        available.acquire();
        return getNextAvailableItem();
    }

    public void putItem(Object x){
        // 将给定项放回池内，标记为未被使用，新增了一个可用项，释放一个许可，请求资源的线程被激活一个
        if (markAsUnused(x))
            available.release();
    }

    protected Object [] items = {}; // 这里存放对象池中的复用对象

    protected boolean[] used = new boolean[MAX_AVAILABLE]; // 用于标示池中的项是否正在被使用

    protected synchronized Object getNextAvailableItem(){
        for(int i = 0 ; i < MAX_AVAILABLE; ++i){
            if (!used[i]){ // 如果当前项未被使用，则获得它
                used[i] = true; // 将当前项标记为已经使用
                return items[i];
            }
        }
        return null;
    }

    protected synchronized boolean markAsUnused(Object item){
        for(int i = 0 ; i < MAX_AVAILABLE; ++i){
            if(item == items[i]){ // 找到给定项的索引
                if(used[i]){
                    used[i] = false; // 将给定项标记为未被使用
                    return true;
                }else
                    return false;
            }
        }
        return false;
    }
}

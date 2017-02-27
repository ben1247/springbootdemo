package com.shuyun.sbd.utils.concurrentDemo.blockingQueueDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Component: 自己实现一个阻塞队列
 * Description:
 * Date: 17/1/22
 *
 * @author yue.zhang
 */
public class MyBlockingQueue {

    private List list = new ArrayList<>();

    public synchronized Object pop() throws InterruptedException {
        while (list.size() == 0)
            this.wait();

        if (list.size()>0)
            return list.remove(0);
        else
            return null;
    }

    public synchronized void put(Object o){
        list.add(o);
        this.notify();
    }
}

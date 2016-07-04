package com.shuyun.sbd.utils.concurrentDemo.blockingQueueDemo;

import java.util.concurrent.BlockingQueue;

/**
 * Component: 以下是 Consumer 类。它只是把对象从队列中抽取出来，然后将它们打印到 System.out。
 * Description:
 * Date: 16/7/4
 *
 * @author yue.zhang
 */
public class Consumer implements Runnable {

    protected BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

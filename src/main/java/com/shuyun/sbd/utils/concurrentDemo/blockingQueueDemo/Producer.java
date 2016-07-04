package com.shuyun.sbd.utils.concurrentDemo.blockingQueueDemo;

import java.util.concurrent.BlockingQueue;

/**
 * Component:以下是 Producer 类。
 * 注意它在每次 put() 调用时是如何休眠一秒钟的。
 * 这将导致 Consumer 在等待队列中对象的时候发生阻塞
 * Description:
 * Date: 16/7/4
 *
 * @author yue.zhang
 */
public class Producer implements Runnable {

    protected BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.put("1");
            Thread.sleep(1000);
            queue.put("2");
            Thread.sleep(1000);
            queue.put("3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

package com.shuyun.sbd.utils.concurrentDemo.blockingQueueDemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Component: 阻塞队列
 * Description:
 * Date: 16/7/4
 *
 * @author yue.zhang
 */
public class BlockingQueueMain {

    public static void main(String [] args) throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        Thread.sleep(4000);
    }

}

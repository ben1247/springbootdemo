package com.shuyun.sbd.utils.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟高并发请求
 *
 * 1. Semaphore
 * Semaphore是一种基于计数的信号量。它可以设定一个阈值，基于此，多个线程竞争获取许可信号，
 * 做完自己的申请后归还，超过阈值后，线程申请许可信号将会被阻塞。Semaphore可以用来构建一些对象池，
 * 资源池之类的，比如数据库连接池，我们也可以创建计数为1的Semaphore，将其作为一种类似互斥锁的机制，
 * 这也叫二元信号量，表示两种互斥状态。
 *
 * 2. CountDownLatch
 * CountDownLatch这个类能够使一个线程等待其他线程完成各自的工作后再执行。
 * 例如，应用程序的主线程希望在负责启动框架服务的线程已经启动所有的框架服务之后再执行。
 * CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量。
 * 每当一个线程完成了自己的任务后，计数器的值就会减1。当计数器值到达0时，它表示所有的线程已经完成了任务，
 * 然后在闭锁上等待的线程就可以恢复执行任务。
 *
 *
 *
 *
 * Created by yuezhang on 18/4/29.
 */
public class HighConcurrency {

    // 请求总数
    private static int clientTotal = 5000;

    // 同时并发执行的线程数
    private static int threadTotal = 200;

    private static AtomicInteger count = new AtomicInteger();

    private static void add(){
        count.incrementAndGet();
    }

    public static void main(String [] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        // 信号量，此处用于控制并发的线程数
        final Semaphore semaphore = new Semaphore(threadTotal);

        // 闭锁，可实现计数器递减
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 执行此方法用于获取执行许可，当总计未释放的许可数不超过200s时，允许通行。否则线程阻塞等待，直到获取许可
                        semaphore.acquire();

                        add();

                        // 释放许可
                        semaphore.release();

                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        // 闭锁减一
                        countDownLatch.countDown();
                    }
                }
            });
        }

        // 线程阻塞，直到闭锁值为0，阻塞才会释放，继续往下执行
        countDownLatch.await();

        executorService.shutdown();

        System.out.println("count: " + count);
    }

}

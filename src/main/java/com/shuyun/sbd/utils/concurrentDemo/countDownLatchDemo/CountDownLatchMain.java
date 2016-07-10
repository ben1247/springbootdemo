package com.shuyun.sbd.utils.concurrentDemo.countDownLatchDemo;

import java.util.concurrent.CountDownLatch;

/**
 * Component: 闭锁 CountDownLatch
 java.util.concurrent.CountDownLatch 是一个并发构造，它允许一个或多个线程等待一系列指定操作的完成。
 CountDownLatch 以一个给定的数量初始化。countDown() 每被调用一次，这一数量就减一。通过调用 await() 方法之一，线程可以阻塞等待这一数量到达零。
 以下是一个简单示例。Decrementer 三次调用 countDown() 之后，等待中的 Waiter 才会从 await() 调用中释放出来。
 * Description:
 * Date: 16/7/5
 *
 * @author yue.zhang
 */
public class CountDownLatchMain {

    public static void main(String [] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        Waiter waiter = new Waiter(latch);

        Decrementer decrementer = new Decrementer(latch);

        new Thread(waiter).start();

        new Thread(decrementer).start();

        Thread.sleep(4000);
    }

}

package com.shuyun.sbd.utils.concurrentDemo.countDownLatchDemo;

import java.util.concurrent.CountDownLatch;

/**
 * Component:
 * Description:
 * Date: 16/7/5
 *
 * @author yue.zhang
 */
public class Waiter implements Runnable {

    private CountDownLatch latch;

    public Waiter(CountDownLatch latch){
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Waiter Released");
    }
}

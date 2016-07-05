package com.shuyun.sbd.utils.concurrentDemo.countDownLatchDemo;

import java.util.concurrent.CountDownLatch;

/**
 * Component:
 * Description:
 * Date: 16/7/5
 *
 * @author yue.zhang
 */
public class Decrementer implements Runnable {

    private CountDownLatch latch;

    public Decrementer(CountDownLatch latch){
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            latch.countDown();
            System.out.println("释放一个，剩余：" + latch.getCount());

            Thread.sleep(1000);
            latch.countDown();
            System.out.println("释放一个，剩余：" + latch.getCount());

            Thread.sleep(1000);
            latch.countDown();
            System.out.println("释放一个，剩余：" + latch.getCount());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

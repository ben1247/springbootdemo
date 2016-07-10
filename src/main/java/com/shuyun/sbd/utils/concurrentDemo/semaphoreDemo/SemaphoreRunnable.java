package com.shuyun.sbd.utils.concurrentDemo.semaphoreDemo;

import java.util.concurrent.Semaphore;

/**
 * Component:
 * Description:
 * Date: 16/7/10
 *
 * @author yue.zhang
 */
public class SemaphoreRunnable implements Runnable {

    private Semaphore semaphore;

    public SemaphoreRunnable(Semaphore semaphore){
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            System.out.println("线程：" + Thread.currentThread().getName() + " 排队拿许可证....");
            semaphore.acquire();
            System.out.println("线程：" + Thread.currentThread().getName() + " 获取到了许可证，开始工作！！！！！！");
            for(int i= 1 ; i<=3; i++){
                Thread.sleep(1000);
                System.out.println("1秒过去了");
            }
            System.out.println("线程：" + Thread.currentThread().getName() + " 事情做完啦！！！！！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
            System.out.println("线程：" + Thread.currentThread().getName() + " 释放了许可证");
        }
    }
}

package com.shuyun.sbd.utils.concurrentDemo.semaphoreDemo;

import java.util.concurrent.Semaphore;

/**
 * Component:
 * 计数信号量由一个指定数量的 "许可" 初始化。每调用一次 acquire()，一个许可会被调用线程取走。每调用一次 release()，一个许可会被返还给信号量。因此，在没有任何 release() 调用时，最多有 N 个线程能够通过 acquire() 方法，N 是该信号量初始化时的许可的指定数量。这些许可只是一个简单的计数器。这里没啥奇特的地方。
 * 信号量主要有两种用途：
    1. 保护一个重要(代码)部分防止一次超过 N 个线程进入。
    2. 在两个线程之间发送信号。
   如果你将信号量用于保护一个重要部分，试图进入这一部分的代码通常会首先尝试获得一个许可，然后才能进入重要部分(代码块)，执行完之后，再把许可释放掉。
 * Description:
 * Date: 16/7/10
 *
 * @author yue.zhang
 */
public class SemaphoreMain {

    public static void main(String [] args){
        Semaphore semaphore = new Semaphore(1);

        SemaphoreRunnable semaphoreRunnable1 = new SemaphoreRunnable(semaphore);
        SemaphoreRunnable semaphoreRunnable2 = new SemaphoreRunnable(semaphore);
        SemaphoreRunnable semaphoreRunnable3 = new SemaphoreRunnable(semaphore);
        SemaphoreRunnable semaphoreRunnable4 = new SemaphoreRunnable(semaphore);

        new Thread(semaphoreRunnable1).start();
        new Thread(semaphoreRunnable2).start();
        new Thread(semaphoreRunnable3).start();
        new Thread(semaphoreRunnable4).start();


    }

}

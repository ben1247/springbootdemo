package com.shuyun.sbd.threads.concurrent.base;

/**
 * Component: 军队线程，模拟作战双方的行为
 * Description:
 * Date: 16/6/21
 *
 * @author yue.zhang
 */
public class ArmyRunnable implements Runnable {

    // volatile 保证了线程可以正确的读取其他线程写入的值 ， 用于控制线程结束
    // 可见性 ref JMM , happens-before原则
    // 网络解释：用在多线程，同步变量。 线程为了提高效率，将某成员变量(如A)拷贝了一份（如B），线程中对A的访问其实访问的是B。只在某些动作时才进行A和B的同步。因此存在A和B不一致的情况。volatile就是用来避免这种情况的。volatile告诉jvm， 它所修饰的变量不保留拷贝，直接访问主内存中的（也就是上面说的A)
    // 注意：当变量的值由自身的上一个决定时，如n=n+1、n++ 等，volatile关键字将失效，只有当变量的值和自身上一个值无关时对该变量的操作才是原子级别的，如n = m + 1，这个就是原级别的
    volatile boolean keepRunning =true;

    @Override
    public void run() {
        while (keepRunning){
            // 发动5连击
            for (int i = 0 ; i < 5 ; i++){
                System.out.println(Thread.currentThread().getName() + "进攻对方[" + i + "]");
                // 让出了处理器时间，下次该谁进攻还不一定呢！
                Thread.yield();
            }
        }
        System.out.println(Thread.currentThread().getName() + "结束进攻");
    }
}

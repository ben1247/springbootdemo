package com.shuyun.sbd.threads.concurrent.base;

/**
 * Component: 关键人物Runnable
 * Description:
 * Date: 16/6/21
 *
 * @author yue.zhang
 */
public class KeyPersonRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始了战斗");

        for(int i = 0 ; i < 10 ; i ++){
            System.out.println(Thread.currentThread().getName() + "左突右杀，攻打隋军");
        }

        System.out.println(Thread.currentThread().getName() + "结束了战斗");
    }
}

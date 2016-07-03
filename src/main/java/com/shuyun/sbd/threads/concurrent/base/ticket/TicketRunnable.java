package com.shuyun.sbd.threads.concurrent.base.ticket;

/**
 * Component:
 * Description:
 * Date: 16/6/26
 *
 * @author yue.zhang
 */
public class TicketRunnable {

    public static void main(String [] args){
        MyRunnable mr = new MyRunnable();
        // 创建3个线程来模拟三个售票窗口
        Thread th1 = new Thread(mr,"窗口1");
        Thread th2 = new Thread(mr,"窗口2");
        Thread th3 = new Thread(mr,"窗口3");

        // 启动这三个线程
        th1.start();
        th2.start();
        th3.start();
    }
}

class MyRunnable implements Runnable{

    private int ticketCount = 5; // 一共有5张火车票

    @Override
    public void run() {
        while(ticketCount > 0){
            ticketCount --; // 如果还有票，就卖掉一张
            System.out.println(Thread.currentThread().getName() + " 卖了一张票，剩余票数为：" + ticketCount);
        }
    }
}
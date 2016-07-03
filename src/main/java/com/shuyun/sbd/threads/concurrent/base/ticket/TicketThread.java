package com.shuyun.sbd.threads.concurrent.base.ticket;

/**
 * Component:
 * Description:
 * Date: 16/6/26
 *
 * @author yue.zhang
 */
public class TicketThread {

    public static void main(String [] args){
        // 创建三个线程，模拟三个窗口卖票

        MyThread mt1 = new MyThread("窗口1");
        MyThread mt2 = new MyThread("窗口2");
        MyThread mt3 = new MyThread("窗口3");

        // 启动这3个线程
        mt1.start();
        mt2.start();
        mt3.start();

    }

}

class MyThread extends Thread{

    private int ticketCount = 5; // 一共有5张火车票

    private String name; //窗口，也既是线程的名字

    public MyThread(String name){
        this.name =name;
    }

    @Override
    public void run() {
        while(ticketCount > 0){
            ticketCount --; // 如果还有票，就卖掉一张
            System.out.println(name + " 卖了一张票，剩余票数为：" + ticketCount);
        }
    }
}
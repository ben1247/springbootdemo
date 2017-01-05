package com.shuyun.sbd.utils.mutiThread.pool;

/**
 * Component: 任务对象
 * Description:
 * Date: 17/1/6
 *
 * @author yue.zhang
 */
public class MyThread implements Runnable {

    private String name;

    public MyThread(){

    }

    public MyThread(String name){
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100); // 使用sleep方法代替一个具体功能的执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

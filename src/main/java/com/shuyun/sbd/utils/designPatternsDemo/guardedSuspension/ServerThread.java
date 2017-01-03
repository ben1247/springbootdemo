package com.shuyun.sbd.utils.designPatternsDemo.guardedSuspension;

/**
 * Component: 服务端进程，用于处理用户的请求操作
 * Description:
 * Date: 17/1/3
 *
 * @author yue.zhang
 */
public class ServerThread extends Thread {

    private RequestQueue requestQueue; // 请求队列

    public ServerThread(RequestQueue requestQueue, String name){
        super(name);
        this.requestQueue = requestQueue;
    }

    public void run(){
        while (true){
            final Request request = requestQueue.getRequest();
            try {
                Thread.sleep(100);  // 模拟请求处理耗时
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "handles " + request);
        }
    }

}

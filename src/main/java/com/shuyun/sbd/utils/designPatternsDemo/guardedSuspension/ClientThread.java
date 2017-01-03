package com.shuyun.sbd.utils.designPatternsDemo.guardedSuspension;

/**
 * Component: 客户端发起请求进程
 * Description:
 * Date: 17/1/3
 *
 * @author yue.zhang
 */
public class ClientThread extends Thread{

    private RequestQueue requestQueue;

    public ClientThread(RequestQueue requestQueue,String name){
        super(name);
        this.requestQueue = requestQueue;
    }

    public void run(){
        for(int i = 0; i < 10 ; i++){
            Request request = new Request("RequestID: " + i + " Thread_Name: " + Thread.currentThread().getName());
            requestQueue.addRequest(request);
            try {
                Thread.sleep(10);  // 客户端处理速度，快于服务端处理速度
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ClientThread Name is: " + Thread.currentThread().getName());
        }
        System.out.println(Thread.currentThread().getName()+ " request end");
    }

}

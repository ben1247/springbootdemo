package com.shuyun.sbd.utils.designPatternsDemo.guardedSuspension.future;

/**
 * Component:
 * Description:
 * Date: 17/1/3
 *
 * @author yue.zhang
 */
public class ServerThread extends Thread{

    private RequestQueue requestQueue;

    public ServerThread(RequestQueue requestQueue , String name){
        super(name);
        this.requestQueue = requestQueue;
    }

    public void run(){
        while (true){
            final Request request = requestQueue.getRequest();
            final FutureData future = (FutureData)request.getResponse();
            // RealData的创建比较耗时
            RealData realData = new RealData(request.getName());
            // 处理完通知客户进程
            future.setRealData(realData);

            System.out.println(Thread.currentThread().getName() + "handles " + request);
        }
    }

}

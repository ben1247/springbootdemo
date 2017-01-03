package com.shuyun.sbd.utils.designPatternsDemo.guardedSuspension.future;

import java.util.ArrayList;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 17/1/3
 *
 * @author yue.zhang
 */
public class ClientThread extends Thread {

    private RequestQueue requestQueue;

    private List<Request> myRequest = new ArrayList<>();

    public ClientThread(RequestQueue requestQueue , String name){
        super(name);
        this.requestQueue = requestQueue;
    }

    public void run(){
        for(int i = 0 ; i < 10 ; i++){
            Request request = new Request("RequestID: " + i + " Thread_Name: " + Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + " requests " + request);
            // 设置一个FutureData的返回值
            request.setResponse(new FutureData());
            requestQueue.addRequest(request);
            // 发送请求
            myRequest.add(request);
            // 这里可以做一些额外的业务处理，等待服务端装配数据
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //取得服务端的返回值
            for(Request req : myRequest){
                System.out.println("ClientThread Name is : " + Thread.currentThread().getName() + " Reponse is:  "
                        // 如果服务器还没有处理完，这里会阻塞
                        + req.getResponse().getContent());
            }
        }
    }


}

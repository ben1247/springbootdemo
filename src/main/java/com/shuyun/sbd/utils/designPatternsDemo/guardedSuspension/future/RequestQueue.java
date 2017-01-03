package com.shuyun.sbd.utils.designPatternsDemo.guardedSuspension.future;


import java.util.LinkedList;

/**
 * Component: request的集合，维护系统的request请求列表
 * Description:
 * Date: 17/1/3
 *
 * @author yue.zhang
 */
public class RequestQueue {

    private LinkedList<Request> queue = new LinkedList<>();

    public synchronized Request getRequest(){
        while (queue.size() == 0){
            try {
                wait(); // 等待知道有新的Request加入
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return queue.remove(); // 返回Request队列中的第一个请求
    }

    public synchronized void addRequest(Request request){
        queue.add(request); // 加入新的request请求
        notifyAll(); // 通知getRequest()方法
    }

}

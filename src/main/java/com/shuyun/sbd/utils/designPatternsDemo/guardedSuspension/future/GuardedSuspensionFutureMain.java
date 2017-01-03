package com.shuyun.sbd.utils.designPatternsDemo.guardedSuspension.future;

/**
 * Component:
 * Description:
 * Date: 17/1/3
 *
 * @author yue.zhang
 */
public class GuardedSuspensionFutureMain {

    public static void main(String [] args){
        RequestQueue requestQueue = new RequestQueue(); // 请求队列

        for(int i = 0 ; i < 10 ; i++){
            new ServerThread(requestQueue,"ServerThread" + i).start();
        }

        for(int i = 0 ; i < 10 ; i++){
            new ClientThread(requestQueue,"ClientThread" + i).start();
        }
    }

}

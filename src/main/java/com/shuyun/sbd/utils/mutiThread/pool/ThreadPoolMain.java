package com.shuyun.sbd.utils.mutiThread.pool;

/**
 * Component:
 * Description:
 * Date: 17/1/6
 *
 * @author yue.zhang
 */
public class ThreadPoolMain {

    public static void main(String [] args){

        for(int i = 0 ; i < 1000; i++){
            ThreadPool.getInstance().start(new MyThread("testThreadPool"+i));
        }

    }

}

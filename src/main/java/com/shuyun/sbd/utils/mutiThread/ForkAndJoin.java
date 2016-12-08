package com.shuyun.sbd.utils.mutiThread;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Component:
 * Description:
 * Date: 16/12/3
 *
 * @author yue.zhang
 */
public class ForkAndJoin {

    public static void threadAndJoin(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    System.out.println("I am running in a separate thread!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread finish");
    }

//    public static void executorsDemo(){
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//
//        List<Future<Long>> results = executor.invokeAll(asList(
//
//        new Sum(0, 10), new Sum(100, 1_000), new Sum(10_000, 1_000_000)
//
//        ));
//
//        executor.shutdown();
//
//        for (Future<Long> result : results) {
//            System.out.println(result.get());
//        }
//
//    }

    public static void main(String [] args){
        threadAndJoin();
    }

}

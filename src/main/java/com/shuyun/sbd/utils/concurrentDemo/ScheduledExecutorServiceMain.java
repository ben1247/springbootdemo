package com.shuyun.sbd.utils.concurrentDemo;

import java.util.concurrent.*;

/**
 * Component:  定时执行者服务 ScheduledExecutorService
 * Description:
 * Date: 16/7/18
 *
 * @author yue.zhang
 */
public class ScheduledExecutorServiceMain {

    public static void main(String [] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println("Executed !");

                return "Called";
            }
        },2, TimeUnit.SECONDS);

        System.out.println("result = " + scheduledFuture.get());

        scheduledExecutorService.shutdown();
    }

}

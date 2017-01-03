package com.shuyun.sbd.utils.future.jdkFuture;

import java.util.concurrent.*;

/**
 * Component:
 * Description:
 * Date: 16/12/29
 *
 * @author yue.zhang
 */
public class FutureMain {

    public static void main(String [] args) throws ExecutionException, InterruptedException {

        // 构建FutureTask
        FutureTask<String> future = new FutureTask<>(new RealData("a"));

        ExecutorService executor = Executors.newFixedThreadPool(1);

        // 执行FutureTask,相当于上例中的client.request("a") 发送请求
        // 在这里开启线程进行RealData的call()执行
        executor.submit(future);
        System.out.println("请求完毕");

        try {
            // 这里可以用一个sleep代替对其他业务逻辑的处理
            // 在处理这些业务逻辑的过程中，RealData被创建，从而充分利用了等待时间
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("数据＝" + future.get());

        executor.shutdown();


    }

}

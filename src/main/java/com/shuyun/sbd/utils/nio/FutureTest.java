package com.shuyun.sbd.utils.nio;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Component:
 * Description:
 * Date: 15/9/5
 *
 * @author yue.zhang
 */
public class FutureTest {

    private static void future1(){
        FutureTask<String> future = new FutureTask<String>(new Callable<String>() { //使用Callable接口作为构造参数
            public String call() throws InterruptedException {
                //真正的任务在这里执行，这里的返回值类型为String，可以为任意类型
                Thread.sleep(2000);
                return "盖世猫王_" + new Date().getTime();
            }
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(future);

        try {
            String result = future.get(3000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5秒。同样可以用future.get()，不设置执行超时时间取得结果
            System.out.println(result);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
            future.cancel(true);
        } catch (ExecutionException e) {
            System.out.println("ExecutionException");
            future.cancel(true);
        } catch (TimeoutException e) {
            System.out.println("TimeoutException");
            future.cancel(true);
        } finally {
            executor.shutdown();
        }
        System.out.println("我不想等了");
    }

    private static void future2(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Callable<String>() {
            public String call() throws InterruptedException {
                Thread.sleep(2000);
                return "盖世猫王_" + new Date().getTime();
            }
        });

        executor.execute((FutureTask)future);
        try {
            String result = future.get(3000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5秒。同样可以用future.get()，不设置执行超时时间取得结果
            System.out.println(result);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
            future.cancel(true);
        } catch (ExecutionException e) {
            System.out.println("ExecutionException");
            future.cancel(true);
        } catch (TimeoutException e) {
            System.out.println("TimeoutException");
            future.cancel(true);
        } finally {
            executor.shutdown();
        }
        System.out.println("我不想等了");
    }

    public static void main(String [] args){
        future1();
//        future2();
    }

}

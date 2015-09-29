package com.shuyun.sbd.utils.nio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Component:
 * Description:
 * Date: 15/9/5
 *
 * @author yue.zhang
 */
public class CallableAndFuture {

    private static void version1(){
        Callable<Integer> callable = new Callable<Integer>() {
            public Integer call() throws Exception {
                return new Random().nextInt(100);
            }
        };
        FutureTask<Integer> future = new FutureTask<Integer>(callable);
        new Thread(future).start();
        try {
            Thread.sleep(100);// 可能做一些事情
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 代码是不是简化了很多，ExecutorService继承自Executor，它的目的是为我们管理Thread对象，
     * 从而简化并发编程，Executor使我们无需显示的去管理线程的生命周期，是JDK 5之后启动任务的首选方式
     */
    private static void version2(){
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future<Integer> future = threadPool.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                return new Random().nextInt(100);
            }
        });
        try {
            Thread.sleep(100);// 可能做一些事情
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行多个带返回值的任务，并取得多个返回值，
     * 其实也可以不使用CompletionService，可以先创建一个装Future类型的集合，用Executor提交的任务返回值添加到集合中，最后遍历集合取出数据
     */
    private static void version3(){
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(threadPool);
        for(int i = 1; i < 5; i++) {
            final int taskID = i;
            cs.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    return taskID;
                }
            });
        }
        // 可能做一些事情
        for(int i = 1; i < 5; i++) {
            try {
                System.out.println(cs.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 实现了异步操作
     */
    private static void version4(){
        ExecutorService threadPool = Executors.newCachedThreadPool();
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
        for(int i = 1 ; i < 5; i++){
            final int taskID = i;
            futures.add(threadPool.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    Thread.sleep(1000);
                    System.out.println("future: " + taskID);
                    return taskID;
                }
            }));
        }
        for(Future<Integer> future : futures){
            try {
                System.out.println("还没结束吗");
//                System.out.println("结果");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        version4();
    }


}

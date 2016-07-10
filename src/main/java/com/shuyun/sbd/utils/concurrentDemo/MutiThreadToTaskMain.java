package com.shuyun.sbd.utils.concurrentDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Component: 多个线程处理定时任务
 * Description:
 * Date: 16/7/3
 *
 * @author yue.zhang
 */
public class MutiThreadToTaskMain {


    public void execute(List<String> dataList){

        // 数据大小
        int dataSize = dataList.size();

        // cpu个数
        int cpus = Runtime.getRuntime().availableProcessors();

        // 线程数
        int threads = cpus + 1;

        // 总页数(需要的线程数)
        int pageCount;

        // 每个线程处理的数据数量
        int pageSize;

        if(dataSize < threads){
            pageSize = dataSize;
            pageCount = 1;
        }else {
            pageSize = dataSize / threads;
            pageCount = threads;
        }

        // 开启线程池（一页对应一个线程）
        ExecutorService executorService = Executors.newFixedThreadPool(pageCount);

        // 所有线程都执行完后 需要执行的任务，目前只记录日志即可
        CyclicBarrier barrier = new CyclicBarrier(pageCount,new TaskFinishThread());

        try{
            List<String> partDataList;
            for(int i = 1 ; i <= pageCount; i++){
                // 拆分数据给每个线程
                if(i != pageCount){
                    partDataList = dataList.subList((i-1) * pageSize , i * pageSize);
                }else{
                    // 最后一页
                    partDataList = dataList.subList((i-1) * pageSize , dataSize);
                }
                executorService.execute(new ExecTaskThread(barrier,partDataList));
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            executorService.shutdown();//关闭线程池(线程池线程执行完后关闭线程池)
            System.out.println("executorService shutdown");
        }


    }

    /**
     * 任务执行程序
     */
    private class ExecTaskThread implements Runnable {

        private CyclicBarrier barrier;

        private List<String> partDataList;

        public ExecTaskThread(CyclicBarrier barrier ,List<String> partDataList){
            this.barrier = barrier;
            this.partDataList = partDataList;
        }

        @Override
        public void run() {
            try {
                long st = System.currentTimeMillis();
                for(String data : partDataList){
                    System.out.println("ExecTaskThread to run , name: " + Thread.currentThread().getName() + "  data: " + data);
//                    Thread.sleep(1000);
                }
                long et = System.currentTimeMillis();
                System.out.println("ExecTaskThread running , name: " + Thread.currentThread().getName() + "  host: " + (et-st) + " ms");

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    System.out.println("ExecTaskThread finish run , name: " + Thread.currentThread().getName() + " getParties:" + barrier.getParties() + ",getNumberWaiting:" + barrier.getNumberWaiting() +",isBroken " + barrier.isBroken());
                    barrier.await();// 通知其他线程该线程已处理完毕
                    System.out.println("ExecTaskThread finish await , name: " + Thread.currentThread().getName() + " getParties:" + barrier.getParties() + ",getNumberWaiting:" + barrier.getNumberWaiting() +",isBroken " + barrier.isBroken());
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 任务结束线程
     */
    private class TaskFinishThread implements Runnable {

        @Override
        public void run() {
            System.out.println("TaskFinishThread to run , name: " + Thread.currentThread().getName());
        }
    }

    public static void main(String [] args){
        List<String> dataList = new ArrayList<>();
        for(int i = 1 ; i <= 131; i++){
            dataList.add(i + "");
        }

        new MutiThreadToTaskMain().execute(dataList);
    }

}

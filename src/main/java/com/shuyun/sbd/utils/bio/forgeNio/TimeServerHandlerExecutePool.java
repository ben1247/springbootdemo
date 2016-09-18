package com.shuyun.sbd.utils.bio.forgeNio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Component: 线程池
 * Description:
 * Date: 16/9/18
 *
 * @author yue.zhang
 */
public class TimeServerHandlerExecutePool {

    private ExecutorService executor;

    /**
     * 构造函数
     * @param maxPoolSize 最大线程池大小
     * @param queueSize   队列大小
     */
    public TimeServerHandlerExecutePool(int maxPoolSize , int queueSize){
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),maxPoolSize,120L, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task){
        executor.execute(task);
    }

}

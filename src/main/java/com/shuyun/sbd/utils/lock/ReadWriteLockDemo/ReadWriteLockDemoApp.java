package com.shuyun.sbd.utils.lock.ReadWriteLockDemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Component:
 * Description:
 * Date: 16/9/16
 *
 * @author yue.zhang
 */
public class ReadWriteLockDemoApp {

    public static void main(String [] args){
        ReadWriteLock lock = new ReentrantReadWriteLock();

        final Lock readLock = lock.readLock();
        final Lock writeLock = lock.writeLock();
        final Resource resource = new Resource();
        final Random random = new Random();

        // 写操作
        for(int i = 0 ; i < 20 ; i++){
            new Thread(){
                @Override
                public void run() {
                    writeLock.lock();
                    try{
                        resource.setValue(resource.getValue() + 1);
                        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "--" + Thread.currentThread() + "获取了写锁，修正数据为：" + resource.getValue());
                        Thread.sleep(random.nextInt(1000)); // 随机休眠
                    }catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        writeLock.unlock();
                    }
                }
            }.start();
        }

        // 读操作
        for (int i = 0 ; i < 20 ; i++){
            new Thread(){
                @Override
                public void run() {
                    readLock.lock();
                    try {
                        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "--" + Thread.currentThread() + "获取了读锁，读取的数据为：" + resource.getValue());
                        Thread.sleep(random.nextInt(1000)); // 随机休眠
                    }catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        readLock.unlock();
                    }
                }
            }.start();
        }
    }

}

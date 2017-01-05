package com.shuyun.sbd.utils.designPatternsDemo.producerAndConsumer;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Component: 消费者
 * Description:
 * Date: 17/1/5
 *
 * @author yue.zhang
 */
public class Consumer implements Runnable {

    private BlockingQueue<PCData> queue; // 缓冲区

    private static final int SLEEPTIME = 1000;

    public Consumer(BlockingQueue<PCData> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("start Consumer id=" + Thread.currentThread().getId());
        Random r = new Random();
        try{
            while (true){
                PCData data = queue.take();
                if(data != null){
                    int re = data.getIntData() * data.getIntData();
                    System.out.println(MessageFormat.format("{0}*{1}={2}",data.getIntData(),data.getIntData(),re));
                    Thread.sleep(r.nextInt(SLEEPTIME));
                }
            }
        }catch (InterruptedException e){
            e.fillInStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}

package com.shuyun.sbd.utils.concurrentDemo.exchangerDemo;

import java.util.concurrent.Exchanger;

/**
 * Component:
 * Description:
 * Date: 16/7/10
 *
 * @author yue.zhang
 */
public class ExchangerRunnable implements Runnable {

    private Exchanger exchanger;

    private Object object;

    public ExchangerRunnable(Exchanger exchanger , Object object){
        this.exchanger = exchanger;
        this.object = object;
    }

    @Override
    public void run() {
        try {

            Object previous = this.object;

            this.object = this.exchanger.exchange(this.object);

            System.out.println(
                    Thread.currentThread().getName() +
                            " exchanged " + previous + " for " + this.object
            );

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

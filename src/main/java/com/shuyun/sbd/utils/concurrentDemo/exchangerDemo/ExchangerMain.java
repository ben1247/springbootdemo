package com.shuyun.sbd.utils.concurrentDemo.exchangerDemo;

import java.util.concurrent.Exchanger;

/**
 * Component:
 * Description:
 * Date: 16/7/10
 *
 * @author yue.zhang
 */
public class ExchangerMain {


    public static void main(String [] args){

        Exchanger exchanger = new Exchanger();

        ExchangerRunnable runnable1 = new ExchangerRunnable(exchanger,"A");
        ExchangerRunnable runnable2 = new ExchangerRunnable(exchanger,"B");
        ExchangerRunnable runnable3 = new ExchangerRunnable(exchanger,"C");
        ExchangerRunnable runnable4 = new ExchangerRunnable(exchanger,"D");

        new Thread(runnable1).start();
        new Thread(runnable2).start();
        new Thread(runnable3).start();
        new Thread(runnable4).start();
    }

}

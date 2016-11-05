package com.shuyun.sbd.utils.guava;

import com.google.common.util.concurrent.*;
import com.shuyun.sbd.domain.Trade;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Component:
 * Description:
 * Date: 16/10/25
 *
 * @author yue.zhang
 */
public class ListenableFutureTest {

    public static void main(String [] args){
        int thread = Runtime.getRuntime().availableProcessors() + 1;

        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(thread));
        ListenableFuture future = service.submit(new Callable<Trade>() {

            @Override
            public Trade call() throws Exception {
                Trade trade = new Trade("tid"+ Math.random(),"1",Math.random());
                System.out.println("call trade: ->" + trade.toString());
                return trade;
            }
        });

        Futures.addCallback(future, new FutureCallback<Trade>() {
            @Override
            public void onSuccess(Trade trade) {
                System.out.println("callback on success  trade: ->" + trade.toString());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

    }

}

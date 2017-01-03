package com.shuyun.sbd.utils.future.myFuture;

/**
 * Component: client主要实现了获取FutureData，开启构造RealData的线程，并在接受请求后，很快返回FutureData
 * Description:
 * Date: 16/12/29
 *
 * @author yue.zhang
 */
public class Client {

    public Data request(final String questStr){
        final FutureData future = new FutureData();
        new Thread(){
            public void run(){
                // RealData的构建很慢，所以在单独的线程中进行
                RealData realData = new RealData(questStr);
                future.setRealData(realData);
            }
        }.start();

        return future;
    }

}

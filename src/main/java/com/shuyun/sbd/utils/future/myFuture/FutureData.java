package com.shuyun.sbd.utils.future.myFuture;

/**
 * Component: FutureData实现了一个快速返回的RealData包装，它只是一个包装，或者说是一个RealData的虚拟实现。
 * 因此，它可以很快被构造并返回。当使用FutureData的getResult()方法时，程序会阻塞，等待RealData被注入到程序中，
 * 才使用RealData的getResult()方法返回。
 * Description:
 * Date: 16/12/29
 *
 * @author yue.zhang
 */
public class FutureData implements Data {

    protected RealData realData = null; // FutureData是RealData的包装

    protected boolean isReady = false;

    public synchronized void setRealData(RealData realData){
        if(isReady){
            return;
        }
        this.realData = realData;
        isReady = true;
        notifyAll(); // RealData已经被注入，通知getResult()
    }

    @Override
    public synchronized String getResult() { // 会等待RealData构造完成
        while(!isReady){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.result;
    }
}

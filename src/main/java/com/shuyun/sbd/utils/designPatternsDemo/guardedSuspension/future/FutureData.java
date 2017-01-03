package com.shuyun.sbd.utils.designPatternsDemo.guardedSuspension.future;

/**
 * Component:
 * Description:
 * Date: 17/1/3
 *
 * @author yue.zhang
 */
public class FutureData implements Data{

    private RealData realData = null; // FutureData是RealData的包装

    private boolean isReady = false;

    public synchronized void setRealData(RealData realData){
        if (isReady){
            return;
        }
        this.realData = realData;
        isReady = true;
        notifyAll();
    }

    @Override
    public String getContent() {
        while (!isReady){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.getContent();
    }
}

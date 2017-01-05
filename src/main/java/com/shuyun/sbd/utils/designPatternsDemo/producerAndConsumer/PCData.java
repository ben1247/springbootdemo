package com.shuyun.sbd.utils.designPatternsDemo.producerAndConsumer;

/**
 * Component: PCData作为生产者和消费者之间的共享数据模型
 * Description:
 * Date: 17/1/5
 *
 * @author yue.zhang
 */
public class PCData {

    private final int intData;

    public PCData(int d){
        this.intData = d;
    }

    public int getIntData() {
        return intData;
    }

    @Override
    public String toString() {
        return "PCData{" +
                "intData=" + intData +
                '}';
    }
}

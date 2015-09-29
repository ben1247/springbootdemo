package com.shuyun.sbd.utils.designPatternsDemo.proxy;

/**
 * Component:
 * Description:
 * Date: 15/8/2
 *
 * @author yue.zhang
 */
public class RealSubject extends Subject {

    @Override
    public void request() {
        System.out.println("真实请求");
    }
}

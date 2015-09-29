package com.shuyun.sbd.utils.designPatternsDemo.proxy;

/**
 * Component:
 * Description:
 * Date: 15/8/2
 *
 * @author yue.zhang
 */
public class Proxy extends Subject {

    private RealSubject realSubject;

    @Override
    public void request() {
        if (realSubject == null){
            realSubject = new RealSubject();
        }
        System.out.println("代理下");
        realSubject.request();
    }
}

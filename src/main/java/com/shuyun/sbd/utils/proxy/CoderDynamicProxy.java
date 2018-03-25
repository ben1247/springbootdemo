package com.shuyun.sbd.utils.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 在使用动态代理时，我们需要定义一个位于代理类与委托类之间的中介类，也叫动态代理类
 * Created by yuezhang on 18/3/24.
 */
public class CoderDynamicProxy implements InvocationHandler {

    private ICoder coder;

    public CoderDynamicProxy(ICoder coder) {
        this.coder = coder;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(System.currentTimeMillis());
        Object result = method.invoke(coder,args);
        System.out.println(System.currentTimeMillis());
        return result;
    }
}

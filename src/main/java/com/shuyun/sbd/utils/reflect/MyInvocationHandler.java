package com.shuyun.sbd.utils.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Component:
 * Description:
 * Date: 16/12/8
 *
 * @author yue.zhang
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object obj = null;

    public Object bind(Object obj){
        this.obj = obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object temp = method.invoke(this.obj,args);
        return temp;
    }
}

package com.shuyun.sbd.utils.designPatternsDemo.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Component: JDK自带的动态代理
 * Description:
 * Date: 16/12/19
 *
 * @author yue.zhang
 */
public class JdkDbQueryHandler implements InvocationHandler {

    IDBQuery real = null;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(real == null){
            real = new DBQuery();
        }
        return method.invoke(real,args);
    }
}

package com.shuyun.sbd.utils.designPatternsDemo.proxy.dynamic;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Component:
 * Description:
 * Date: 16/12/19
 *
 * @author yue.zhang
 */
public class CglibDbQueryInterceptor implements MethodInterceptor {

    IDBQuery real = null;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        if(real == null){
            real = new DBQuery();
        }

        return method.invoke(real,objects);
    }
}

package com.shuyun.sbd.utils.designPatternsDemo.proxy.dynamic;

import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * Component:
 * Description:
 * Date: 16/12/19
 *
 * @author yue.zhang
 */
public class DBQueryMain {

    /**
     * 创建JDK代理类
     * @return
     */
    public static IDBQuery createJdkProxy(){
        return (IDBQuery)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{IDBQuery.class},new JdkDbQueryHandler());
    }

    /**
     * 创建Cglib代理类
     * @return
     */
    public static IDBQuery createCglibProxy(){
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new CglibDbQueryInterceptor());
        enhancer.setInterfaces(new Class[]{IDBQuery.class});
        return (IDBQuery)enhancer.create();
    }

    public static void main(String [] args){
        IDBQuery d = createJdkProxy();
        System.out.println(d.request());
        System.out.println(d.response());

        System.out.println("====================================");

        d = createCglibProxy();
        System.out.println(d.request());
        System.out.println(d.response());
    }

}

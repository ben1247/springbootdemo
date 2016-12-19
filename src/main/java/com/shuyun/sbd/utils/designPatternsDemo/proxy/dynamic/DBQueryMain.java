package com.shuyun.sbd.utils.designPatternsDemo.proxy.dynamic;

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

    public static void main(String [] args){
        IDBQuery d = createJdkProxy();
        System.out.println(d.request());
        System.out.println(d.response());
    }

}

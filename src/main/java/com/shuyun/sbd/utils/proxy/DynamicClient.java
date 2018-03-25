package com.shuyun.sbd.utils.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by yuezhang on 18/3/24.
 */
public class DynamicClient {

    public static void main(String [] args){
        // 要代理的对象
        ICoder coder = new JavaCoder("zhangyue");
        // 创建中介类实例
        InvocationHandler handler = new CoderDynamicProxy(coder);
        // 获取类加载器
        ClassLoader c = coder.getClass().getClassLoader();
        // 动态生成代理类
        ICoder proxy = (ICoder) Proxy.newProxyInstance(c,coder.getClass().getInterfaces(),handler);
        // 通过代理类，执行方法
        proxy.implDemands("Modify user manager");
    }

}

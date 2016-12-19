package com.shuyun.sbd.utils.designPatternsDemo.singleton;

/**
 * Component:
 *   使用内部静态类来维护单例的实例
 * Description:
 *   当StaticSingleton被加载时，其内部类并不会被初始化，
 * 故可以确保当StaticSingleton类被载入JVM时，不会初始化单例类，而当getInstance()方法被调用时，才会加载SingleHolder，
 * 从而初始化instance.同时，由于实例的建立是在类加载时完成，故天生对多线程友好，getInstance()方法也不需要使用同步关键字。
 * 因此，这种实现方式同时兼备以上两种实现的优点。
 * Date: 16/12/18
 *
 * @author yue.zhang
 */
public class StaticSingleton {

    private StaticSingleton(){
        System.out.println("StaticSingleton is create");
    }

    private static class SingleHolder{
        private static StaticSingleton instance = new StaticSingleton();
    }

    public static StaticSingleton getInstance(){
        return SingleHolder.instance;
    }

    public static void main(String [] args){
        long st = System.currentTimeMillis();
        for(int i = 0 ; i < 10000000; i++){
            StaticSingleton.getInstance();
        }
        long et = System.currentTimeMillis();

        System.out.println("spend: " + (et - st));
    }

}

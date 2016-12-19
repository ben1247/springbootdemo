package com.shuyun.sbd.utils.designPatternsDemo.singleton;

/**
 * Component: 单例模式－延迟加载机制
 * Description:
 * Date: 16/12/18
 *
 * @author yue.zhang
 */
public class LazySingleton {

    private LazySingleton(){
        System.out.println("LazySingleton is create");
    }

    private static LazySingleton instance = null;

    public static synchronized LazySingleton getInstance(){
        if(instance == null){
            instance = new LazySingleton();
        }
        return instance;
    }

    public static void createString(){
        System.out.println("createString in Singleton2");
    }

    public static void main(String [] args){
        long st = System.currentTimeMillis();
        for(int i = 0 ; i < 10000000; i++){
            LazySingleton.getInstance();
        }
        long et = System.currentTimeMillis();

        System.out.println("spend: " + (et - st));
    }

}

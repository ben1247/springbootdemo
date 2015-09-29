package com.shuyun.sbd.utils.designPatternsDemo.singleton;

/**
 * Component: 单例模式
 * Description:
 * Date: 15/8/12
 *
 * @author yue.zhang
 */
public class Singleton {
    private static volatile Singleton instance = null;
    private Singleton() { }
    public static Singleton getInstance() {
        if(instance == null) {
            synchronized(Singleton.class) {
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

}

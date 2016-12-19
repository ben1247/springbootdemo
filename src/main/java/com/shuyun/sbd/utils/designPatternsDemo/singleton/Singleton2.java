package com.shuyun.sbd.utils.designPatternsDemo.singleton;

/**
 * Component:
 * Description:
 * Date: 16/12/18
 *
 * @author yue.zhang
 */
public class Singleton2 {

    private Singleton2(){
        System.out.println("Singleton2 is create");
    }

    private static Singleton2 instance = new Singleton2(); // 因为instance是成员变量，因此JVM加载单利类时，单利对象就会被创建

    public static Singleton2 getInstance(){
        return instance;
    }

    public static void createString(){
        System.out.println("createString in Singleton2");
    }

    public static void main(String [] args){
        long st = System.currentTimeMillis();
        for(int i = 0 ; i < 10000000; i++){
            Singleton2.getInstance();
        }
        long et = System.currentTimeMillis();

        System.out.println("spend: " + (et - st));

    }

}

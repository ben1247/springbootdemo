package com.shuyun.sbd.demo.jvm;

/**
 * Component:
 * Description:
 * Date: 16/3/5
 *
 * @author yue.zhang
 */
public class StaticDispatch {

    static abstract class Human{

    }

    static class Man extends Human{

    }

    static class Woman extends Human{

    }

    public void sayHello(Human guy){
        System.out.println("hello human");
    }

    public void sayHello(Man man){
        System.out.println("hello man");
    }

    public void sayHello(Woman woman){
        System.out.println("hello woman");
    }

    public static void main(String [] args){
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch sr = new StaticDispatch();
        sr.sayHello(man);
        sr.sayHello(woman);
        sr.sayHello((Man)man);
        sr.sayHello((Woman)woman);
    }
}

package com.shuyun.sbd.utils.designPatternsDemo.builder;

/**
 * Component: 建造者模式
 * Description:
 * Date: 15/8/5
 *
 * @author yue.zhang
 */
public class Main {

    public static void main(String [] args){
        Director director = new Director();

        Builder b1 = new ConcreteBuilder1();
        director.construct(b1);
        b1.getResult().show();

        Builder b2 = new ConcreteBuilder2();
        director.construct(b2);
        b2.getResult().show();

    }
}

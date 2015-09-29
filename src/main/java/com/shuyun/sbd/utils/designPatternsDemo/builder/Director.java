package com.shuyun.sbd.utils.designPatternsDemo.builder;

/**
 * Component: 指挥类
 * Description:
 * Date: 15/8/5
 *
 * @author yue.zhang
 */
public class Director {

    public void construct(Builder builder){
        builder.buildPartA();
        builder.buildPartB();
    }
}

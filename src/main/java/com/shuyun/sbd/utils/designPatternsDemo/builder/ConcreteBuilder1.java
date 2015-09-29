package com.shuyun.sbd.utils.designPatternsDemo.builder;

/**
 * Component:
 * Description:
 * Date: 15/8/5
 *
 * @author yue.zhang
 */
public class ConcreteBuilder1 extends Builder {

    @Override
    public void buildPartA() {
        product.add("部件A");
    }

    @Override
    public void buildPartB() {
        product.add("部件B");
    }

}

package com.shuyun.sbd.utils.designPatternsDemo.builder;

/**
 * Component: 抽象建造者
 * Description:
 * Date: 15/8/5
 *
 * @author yue.zhang
 */
public abstract class Builder {

    protected Product product = new Product();

    public abstract void buildPartA();
    public abstract void buildPartB();

    public Product getResult(){
        return product;
    }
}

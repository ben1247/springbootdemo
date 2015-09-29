package com.shuyun.sbd.utils.designPatternsDemo.decorator;

/**
 * Component: 装饰模式：可用于流程节点流转
 * Description:
 * Date: 15/8/2
 *
 * @author yue.zhang
 */
public class Main {

    public static void main(String [] args){
        Person mw = new Person("猫王");
        System.out.println("第一种打扮：");
        BigTrouser bt = new BigTrouser();
        TShirts ts = new TShirts();

        ts.decorate(mw);
        bt.decorate(ts);
        bt.show();
        
    }
}

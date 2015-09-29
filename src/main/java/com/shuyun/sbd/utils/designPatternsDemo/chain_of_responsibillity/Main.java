package com.shuyun.sbd.utils.designPatternsDemo.chain_of_responsibillity;

/**
 * Component: 责任链模式
 * Description:
 * Date: 15/8/14
 *
 * @author yue.zhang
 */
public class Main {

    public static void main(String [] args){
        Handler h1 = new ConcreteHandler1();
        Handler h2 = new ConcreteHandler2();
        Handler h3 = new ConcreteHandler3();

        h1.setSuccessor(h2);
        h2.setSuccessor(h3);

        h1.handleRequest(7);
        h1.handleRequest(17);
        h1.handleRequest(27);
        h1.handleRequest(37);
    }

}

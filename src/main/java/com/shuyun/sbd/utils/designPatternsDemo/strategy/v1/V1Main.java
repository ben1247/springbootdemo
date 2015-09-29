package com.shuyun.sbd.utils.designPatternsDemo.strategy.v1;

/**
 * Component: 简单工厂实现
 * Description:
 * Date: 15/7/29
 *
 * @author yue.zhang
 */
public class V1Main {

    public static void main(String [] args){
        CashSuper cs1 = CashFactory.createCashAccept(1);
        CashSuper cs2 = CashFactory.createCashAccept(2);
        CashSuper cs3 = CashFactory.createCashAccept(3);

        double res1 = cs1.acceptCash(500);
        double res2 = cs2.acceptCash(500);
        double res3 = cs3.acceptCash(500);

        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
}

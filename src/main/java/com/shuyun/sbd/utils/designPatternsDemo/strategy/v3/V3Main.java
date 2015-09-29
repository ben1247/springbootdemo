package com.shuyun.sbd.utils.designPatternsDemo.strategy.v3;

/**
 * Component: 策略与简单工厂结合
 * Description:
 * 结论：
 * 简单工厂模式需要客户端认识服务端2个类(CashSuper、CashFactory)，
 * 而策略加简单工厂模式只需要客户端认识1个类即可 CashContext，
 * 这就是不同之处
 * Date: 15/7/29
 *
 * @author yue.zhang
 */
public class V3Main {

    public static void main(String [] args){
        CashContext cc1 = new CashContext(1);
        CashContext cc2 = new CashContext(2);
        CashContext cc3 = new CashContext(3);
        double res1 = cc1.getResult(500);
        double res2 = cc2.getResult(500);
        double res3 = cc3.getResult(500);

        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
}

package com.shuyun.sbd.utils.designPatternsDemo.strategy.v2;

import com.shuyun.sbd.utils.designPatternsDemo.strategy.v1.CashNormal;
import com.shuyun.sbd.utils.designPatternsDemo.strategy.v1.CashRebate;
import com.shuyun.sbd.utils.designPatternsDemo.strategy.v1.CashReturn;

/**
 * Component: 策略模式
 * Description:
 * Date: 15/7/29
 *
 * @author yue.zhang
 */
public class V2Main {

    public static void main(String [] args){
        CashContext cc = null;
        int type = 3;
        switch (type){
            case 1 :
                cc = new CashContext(new CashNormal());
                break;
            case 2:
                cc = new CashContext(new CashReturn("300","100"));
                break;
            case 3:
                cc = new CashContext(new CashRebate("0.7"));
                break;
        }

        double money = 500;
        double result = cc.getResult(money);
        System.out.println(result);
    }
}

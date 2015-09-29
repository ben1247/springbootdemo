package com.shuyun.sbd.utils.designPatternsDemo.strategy.v1;

/**
 * Component: 现金收费工厂类
 * Description:
 * Date: 15/7/29
 *
 * @author yue.zhang
 */
public class CashFactory {

    public static CashSuper createCashAccept(int type){
        CashSuper cs = null;
        switch (type){
            // 正常消费
            case 1 :
                cs = new CashNormal();
                break;
            // "满300返100"
            case 2:
                cs = new CashReturn("300","100");
                break;
            // 打7折
            case 3:
                cs = new CashRebate("0.7");
                break;
        }
        return cs;
    }
}

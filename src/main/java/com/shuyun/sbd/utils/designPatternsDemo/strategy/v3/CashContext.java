package com.shuyun.sbd.utils.designPatternsDemo.strategy.v3;

import com.shuyun.sbd.utils.designPatternsDemo.strategy.v1.CashNormal;
import com.shuyun.sbd.utils.designPatternsDemo.strategy.v1.CashRebate;
import com.shuyun.sbd.utils.designPatternsDemo.strategy.v1.CashReturn;
import com.shuyun.sbd.utils.designPatternsDemo.strategy.v1.CashSuper;

/**
 * Component:
 * Description:
 * Date: 15/7/29
 *
 * @author yue.zhang
 */
public class CashContext {

    private CashSuper cs;

    public CashContext(int type){
        switch (type){
            case 1 :
                cs = new CashNormal();
                break;
            case 2:
                cs = new CashReturn("300","100");
                break;
            case 3:
                cs = new CashRebate("0.7");
                break;
        }
    }

    public double getResult(double money){
        return cs.acceptCash(money);
    }
}

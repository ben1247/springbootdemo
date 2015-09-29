package com.shuyun.sbd.utils.designPatternsDemo.strategy.v2;

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

    public CashContext(CashSuper _cs){
        this.cs = _cs;
    }

    public double getResult(double money){
        return cs.acceptCash(money);
    }
}

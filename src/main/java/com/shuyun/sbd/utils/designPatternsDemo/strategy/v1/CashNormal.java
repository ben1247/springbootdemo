package com.shuyun.sbd.utils.designPatternsDemo.strategy.v1;

/**
 * Component: 正常收费
 * Description:
 * Date: 15/7/29
 *
 * @author yue.zhang
 */
public class CashNormal extends CashSuper {
    @Override
    public double acceptCash(double money) {
        return money;
    }
}

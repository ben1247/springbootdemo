package com.shuyun.sbd.utils.designPatternsDemo.strategy.v1;

/**
 * Component: 打折收费子类
 * Description:
 * Date: 15/7/29
 *
 * @author yue.zhang
 */
public class CashRebate extends CashSuper {

    private double moneyRebate;

    public CashRebate(String _moneyRebate){
        this.moneyRebate = Double.parseDouble(_moneyRebate);
    }

    @Override
    public double acceptCash(double money) {
        return money * moneyRebate;
    }
}

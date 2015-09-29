package com.shuyun.sbd.utils.designPatternsDemo.strategy.v1;

/**
 * Component: 返利收费子类
 * Description:
 * Date: 15/7/29
 *
 * @author yue.zhang
 */
public class CashReturn extends CashSuper {

    private double moneyCondition;
    private double moneyReturn;

    public CashReturn(String _moneyCondition , String _moneyReturn){
        this.moneyCondition = Double.parseDouble(_moneyCondition);
        this.moneyReturn = Double.parseDouble(_moneyReturn);
    }

    @Override
    public double acceptCash(double money) {
        double result = money;
        if(money >= moneyCondition){
            result = money - Math.floor(money / moneyCondition) * moneyReturn;
        }
        return result;
    }
}

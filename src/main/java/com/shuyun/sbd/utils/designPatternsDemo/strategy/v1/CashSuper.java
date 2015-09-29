package com.shuyun.sbd.utils.designPatternsDemo.strategy.v1;

/**
 * Component: 现金收费抽象类
 * Description:
 * Date: 15/7/29
 *
 * @author yue.zhang
 */
public abstract class CashSuper {
    /**
     * 参数为原价，返回为当前价
     * @param money
     * @return
     */
    public abstract double acceptCash(double money);
}

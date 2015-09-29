package com.shuyun.sbd.utils.calculator;

/**
 * Component:
 * Description:
 * Date: 15/7/26
 *
 * @author yue.zhang
 */
public class SubOperation extends Operation {
    @Override
    public double getResult() {
        return getNumber1() - getNumber2();
    }
}

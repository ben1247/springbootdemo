package com.shuyun.sbd.utils.designPatternsDemo.decorator;

/**
 * Component:
 * Description:
 * Date: 15/8/2
 *
 * @author yue.zhang
 */
public class BigTrouser extends Finery {
    @Override
    public void show() {
        System.out.println("跨裤");
        component.show();
    }
}

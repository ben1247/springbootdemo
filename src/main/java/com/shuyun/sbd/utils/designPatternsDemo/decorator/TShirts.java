package com.shuyun.sbd.utils.designPatternsDemo.decorator;

/**
 * Component:
 * Description:
 * Date: 15/8/2
 *
 * @author yue.zhang
 */
public class TShirts extends Finery {
    @Override
    public void show() {
        System.out.println("大T恤");
        component.show();
    }
}

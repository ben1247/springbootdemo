package com.shuyun.sbd.utils.designPatternsDemo.memento;

/**
 * Component: 备忘录类
 * Description:
 * Date: 15/8/11
 *
 * @author yue.zhang
 */
public class Memento {

    private String state;

    public Memento(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}

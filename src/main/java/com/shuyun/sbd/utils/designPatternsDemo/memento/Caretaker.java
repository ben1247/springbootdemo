package com.shuyun.sbd.utils.designPatternsDemo.memento;

/**
 * Component: 管理者类
 * Description:
 * Date: 15/8/11
 *
 * @author yue.zhang
 */
public class Caretaker {

    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}

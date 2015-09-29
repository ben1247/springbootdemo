package com.shuyun.sbd.utils.designPatternsDemo.memento;

/**
 * Component: 备忘录模式，用于暂存某个时刻的对象信息和状态，以便后续恢复
 * Description:
 * Date: 15/8/11
 *
 * @author yue.zhang
 */
public class Main {

    public static void main(String [] args){
        Originator o = new Originator();
        o.setState("on");
        o.show();

        Caretaker c = new Caretaker();
        c.setMemento(o.createMemento());

        o.setState("off");
        o.show();

        o.setMemento(c.getMemento());
        o.show();

    }
}

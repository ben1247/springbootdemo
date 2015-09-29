package com.shuyun.sbd.utils.designPatternsDemo.memento;

/**
 * Component: 发起人
 * Description:
 * Date: 15/8/11
 *
 * @author yue.zhang
 */
public class Originator {

    private String state;

    public Memento createMemento(){
        return new Memento(state);
    }

    public void setMemento(Memento memento){
        state = memento.getState();
    }

    public void show(){
        System.out.println("state=" + state);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

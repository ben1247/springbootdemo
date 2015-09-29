package com.shuyun.sbd.utils.designPatternsDemo.observer.v1;

/**
 * Component: 具体的观察者
 * Description:
 * Date: 15/8/6
 *
 * @author yue.zhang
 */
public class ConcreteIObserver implements IObserver {

    private String name;
    private String observerState;
    private ConcreteSubject concreteSubject;

    public ConcreteIObserver(ConcreteSubject _concreteSubject, String _name){
        this.name = _name;
        this.concreteSubject = _concreteSubject;
    }

    public void update() {
        observerState = concreteSubject.getSubjectState();
        System.out.println("观察者"+name+"的新状态是"+observerState);
    }

    public ConcreteSubject getConcreteSubject() {
        return concreteSubject;
    }

    public void setConcreteSubject(ConcreteSubject concreteSubject) {
        this.concreteSubject = concreteSubject;
    }
}

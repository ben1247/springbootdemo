package com.shuyun.sbd.utils.designPatternsDemo.observer.v1;

/**
 * Component:
 * Description:
 * Date: 15/8/6
 *
 * @author yue.zhang
 */
public class Main {

    public static void main(String [] args){

        ConcreteSubject subject = new ConcreteSubject();

        subject.add(new ConcreteIObserver(subject,"X"));
        subject.add(new ConcreteIObserver(subject,"Y"));
        subject.add(new ConcreteIObserver(subject,"Z"));

        subject.setSubjectState("ABC");
        subject.notifyObservers();
    }
}

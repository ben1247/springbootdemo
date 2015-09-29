package com.shuyun.sbd.utils.designPatternsDemo.observer.v1;

import java.util.ArrayList;
import java.util.List;

/**
 * Component: 通知者
 * Description:
 * Date: 15/8/6
 *
 * @author yue.zhang
 */
public abstract class Subject {

    private List<IObserver> observers = new ArrayList<IObserver>();

    public void add(IObserver observer){
        observers.add(observer);
    }

    public void remove(IObserver observer){
        observers.remove(observer);
    }

    /**
     * 通知
     */
    public void notifyObservers(){
        for(IObserver o : observers){
            o.update();
        }
    }
}

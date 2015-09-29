package com.shuyun.sbd.utils.designPatternsDemo.mediator;

/**
 * Component:
 * Description:
 * Date: 15/8/15
 *
 * @author yue.zhang
 */
public class ConcreteColleague3 extends Colleague {

    public ConcreteColleague3(String _name) {
        super(_name);
    }

    @Override
    public void receive(String _message) {
        confirmReceive(_message);
//        this.getMediator().send("吃了很多",this);
    }
}

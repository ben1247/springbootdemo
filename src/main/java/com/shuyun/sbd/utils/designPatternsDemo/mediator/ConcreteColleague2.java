package com.shuyun.sbd.utils.designPatternsDemo.mediator;

/**
 * Component:
 * Description:
 * Date: 15/8/15
 *
 * @author yue.zhang
 */
public class ConcreteColleague2 extends Colleague {


    public ConcreteColleague2(String _name) {
        super(_name);
    }

    @Override
    public void receive(String _message) {
        confirmReceive(_message);
//        this.getMediator().send("吃了一点点",this);
    }
}

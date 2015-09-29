package com.shuyun.sbd.utils.designPatternsDemo.commond;

/**
 * Component:
 * Description:
 * Date: 15/8/13
 *
 * @author yue.zhang
 */
public abstract class Command {

    protected Receiver receiver;

    public Command(Receiver _receiver){
        this.receiver = _receiver;
    }

    abstract public void execute();
}

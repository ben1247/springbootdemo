package com.shuyun.sbd.utils.designPatternsDemo.commond;

/**
 * Component:
 * Description:
 * Date: 15/8/13
 *
 * @author yue.zhang
 */
public class ConcreteCommand extends Command {

    public ConcreteCommand(Receiver _receiver){
        super(_receiver);
    }

    @Override
    public void execute() {
        receiver.action();
    }
}

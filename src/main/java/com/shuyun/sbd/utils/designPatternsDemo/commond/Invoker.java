package com.shuyun.sbd.utils.designPatternsDemo.commond;

/**
 * Component:
 * Description:
 * Date: 15/8/13
 *
 * @author yue.zhang
 */
public class Invoker {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand(){
        command.execute();
    }
}

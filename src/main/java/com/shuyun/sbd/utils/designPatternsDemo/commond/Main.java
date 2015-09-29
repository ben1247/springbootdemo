package com.shuyun.sbd.utils.designPatternsDemo.commond;

/**
 * Component: 命令模式
 * Description: 我感觉这个模式和mvc有点类似，
 * Invoker代表action、
 * Command代表service接口，ConcreteCommand代表service接口的实现
 * Receiver代表DAO
 *
 * Date: 15/8/13
 *
 * @author yue.zhang
 */
public class Main {

    public static void main(String [] args){

        Receiver receiver = new Receiver();

        ConcreteCommand command1 = new ConcreteCommand(receiver);
        ConcreteCommand command2 = new ConcreteCommand(receiver);

        Invoker invoker = new Invoker();
        invoker.setCommand(command1);
        invoker.executeCommand();

        invoker.setCommand(command2);
        invoker.executeCommand();
    }
}

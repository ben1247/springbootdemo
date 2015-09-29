package com.shuyun.sbd.utils.designPatternsDemo.state;

/**
 * Component: 状态模式，解决switch case 和 if else 多分支条件
 * Description:
 * Date: 15/8/8
 *
 * @author yue.zhang
 */
public class Main {

    public static void main(String [] args){

        Work w = new Work();
//        w.setHour(9);
//        w.writeProgram();
//        w.setHour(10);
//        w.writeProgram();
//        w.setHour(12);
//        w.writeProgram();
//        w.setHour(15);
//        w.writeProgram();

        w.setHour(16);
        w.writeProgram();

    }

}

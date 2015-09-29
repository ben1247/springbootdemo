package com.shuyun.sbd.utils.designPatternsDemo.mediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Component: 中介模式，类似与mq发消息
 * Description:
 * Date: 15/8/15
 *
 * @author yue.zhang
 */
public class Main {

    public static void main(String [] args){

        List<Colleague> colleagues = Arrays.asList(new ConcreteColleague1("小张"),new ConcreteColleague2("小李"),new ConcreteColleague3("小王"));

        new ConcreteMediator(colleagues);

        for(Colleague colleague : colleagues){
            if(colleague.getName().equals("小李")){
                colleague.send("大家饭吃了吗");
            }
        }

    }
}

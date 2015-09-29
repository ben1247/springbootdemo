package com.shuyun.sbd.utils.designPatternsDemo.decorator;

/**
 * Component:
 * Description:
 * Date: 15/8/2
 *
 * @author yue.zhang
 */
public class Person {

    private String name;

    public Person(){}

    public Person(String _name){
        this.name = _name;
    }

    public void show(){
        System.out.println("装扮的" + name);
    }
}

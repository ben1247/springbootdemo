package com.shuyun.sbd.utils.reflect;

/**
 * Component:
 * Description:
 * Date: 16/12/8
 *
 * @author yue.zhang
 */
public class RealSubject implements Subject {

    @Override
    public String say(String name, int age) {
        return name + " " + age;
    }
}

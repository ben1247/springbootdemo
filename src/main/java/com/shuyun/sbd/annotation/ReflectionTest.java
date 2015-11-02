package com.shuyun.sbd.annotation;

import java.lang.reflect.Method;

/**
 * Component:
 * Description:
 * Date: 15/10/18
 *
 * @author yue.zhang
 */
public class ReflectionTest {

    public static void main(String[] args){
        Class<AnnoChild> ac = AnnoChild.class;

        for(Method m : ac.getMethods()){
            System.out.println(m.getName());
        }

        System.out.println("===============================");

        for(Method m : ac.getDeclaredMethods()){
            System.out.println(m.getName());
        }


    }

}

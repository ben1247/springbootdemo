package com.shuyun.sbd.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 15/10/17
 *
 * @author yue.zhang
 */
public class AnnoTest {

    public static void main(String[] args) throws ClassNotFoundException {

        Class c = Class.forName("com.shuyun.sbd.annotation.Anno");

        boolean flag = c.isAnnotationPresent(FirstAnnotation.class);
        if(flag){
            FirstAnnotation firstAnnotation = (FirstAnnotation) c.getAnnotation(FirstAnnotation.class);
            System.out.println("first annotation value is :  " + firstAnnotation.value() + " \n");
        }

        for(Method method : c.getMethods()){
            SecondAnnotation anno = method.getAnnotation(SecondAnnotation.class);
            if(anno == null)
                continue;

            System.out.println("second annotation's\nname:\t" + anno.name()
                    + "\nurl:\t" + anno.url());
        }

        for(Field filed : c.getDeclaredFields()){
            ThirdAnnotation anno = filed.getAnnotation(ThirdAnnotation.class);
            if(anno == null){
                continue;
            }
            System.out.println("third annotation value is:  " + anno.value() + " \n");
        }

        Class child = AnnoChild.class;

        for (Field field : child.getDeclaredFields()){
            ThirdAnnotation anno = field.getAnnotation(ThirdAnnotation.class);
            if(anno == null){
                continue;
            }
            System.out.println("child third annotation value is : " + anno.value() + " \n");
        }

    }
}

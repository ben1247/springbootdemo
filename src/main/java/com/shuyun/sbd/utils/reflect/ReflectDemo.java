package com.shuyun.sbd.utils.reflect;

import java.io.Serializable;
import java.lang.reflect.Array;

/**
 * Component: 反射的测试类
 * Description: 单元测试查看 test/java/com/shuyun/sbd/utils/reflect/ReflectTest.java
 * Date: 16/12/8
 *
 * @author yue.zhang
 */

public class ReflectDemo implements Serializable{

    private static final long serialVersionUID = -3169811745988733035L;

    private String property;

    public void reflect1() {
        System.out.println("Java 反射机制 - 调用某个类的方法1.");
    }

    public void reflect2(int age, String name) {
        System.out.println("Java 反射机制 - 调用某个类的方法2.");
        System.out.println("age -> " + age + ". name -> " + name);
    }

    /**
     * 修改数组大小
     * @param obj
     * @param len
     * @return
     */
    public static Object arrayInc(Object obj , int len){
        Class<?> arrType = obj.getClass().getComponentType(); // 获取数组元素的类型
        Object newArr = Array.newInstance(arrType, len);
        int co = Array.getLength(obj);
        System.arraycopy(obj, 0, newArr, 0, co);
        return newArr;
    }

    /**
     * 打印
     * @param obj
     */
    public static void print(Object obj){
        Class<?> c = obj.getClass();
        if(!c.isArray()){
            return;
        }
        int len = Array.getLength(obj);
        System.out.println("数组长度：" + len);
        for(int i = 0 ; i < len; i++){
            System.out.print(Array.get(obj, i));
        }
        System.out.println();
    }

}

package com.shuyun.sbd.demo.jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * Component: 使用MethodHandle来实现Son调用GrandFather的方法
 * Description:
 * Date: 16/3/7
 *
 * @author yue.zhang
 */
public class MethodHandleTest2 {

    class GrandFather{
        void thinking(){
            System.out.println("i am grandfather");
        }
    }

    class Father extends GrandFather{
        void thinking(){
            System.out.println("i am father");
        }
    }

    class Son extends Father{
        void thinking(){
            try{
                MethodType mt = MethodType.methodType(void.class);
                MethodHandle mh = lookup().findVirtual(GrandFather.class,"thinking",mt);
                mh.invoke(this);
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String [] args){
        (new MethodHandleTest2().new Son()).thinking();
    }

}

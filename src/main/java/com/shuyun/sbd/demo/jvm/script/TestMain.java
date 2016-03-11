package com.shuyun.sbd.demo.jvm.script;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Component:
 * Description:
 * Date: 16/3/10
 *
 * @author yue.zhang
 */
public class TestMain {

    public static void main(String [] args){
        try{

            InputStream is = new FileInputStream("/Users/yuezhang/Downloads/old/TestClass.class");
            byte [] b = new byte[is.available()];
            is.read(b);
            is.close();

            System.out.println(JavaClassExecuter.execute(b));
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}

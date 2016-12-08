package com.shuyun.sbd.utils.reflect;

/**
 * Component:
 * Description:
 * Date: 16/12/8
 *
 * @author yue.zhang
 */
public class Factory {

    public static Fruit getInstance(String className){
        Fruit f = null;
        try {
            f = (Fruit) Class.forName(className).newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }

}

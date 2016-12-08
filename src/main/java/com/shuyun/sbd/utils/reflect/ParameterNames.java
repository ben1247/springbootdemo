package com.shuyun.sbd.utils.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Component:
 * Description:
 * Date: 16/12/7
 *
 * @author yue.zhang
 */
public class ParameterNames {

    public static void main(String [] args) throws NoSuchMethodException {
        Method method = ParameterNames.class.getMethod("main", String[].class);
        for(final Parameter parameter : method.getParameters()){
            System.out.println(parameter.getName());
        }

    }

}

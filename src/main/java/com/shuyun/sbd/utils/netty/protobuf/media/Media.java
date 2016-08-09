package com.shuyun.sbd.utils.netty.protobuf.media;

import com.google.protobuf.ByteString;
import com.shuyun.sbd.utils.netty.protobuf.RequestMsgProbuf;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Component:
 * Description:
 * Date: 16/8/7
 *
 * @author yue.zhang
 */
public class Media {

    public static Map<String,MethodBean> methodBeans = new HashMap<>();

    public static Object execute(RequestMsgProbuf.RequestMsg requestMsg){

        Object response = null;

        try {
            String cmd = requestMsg.getCmd();

            MethodBean methodBean = methodBeans.get(cmd);

            Method method = methodBean.getMethod();

            Object bean = methodBean.getBean();

            // 获取目标方法参数类型
            Class parameterType = method.getParameterTypes()[0];
            // 获取目标方法参数类型的所有构造方法
            Constructor<?>[] constructors = parameterType.getDeclaredConstructors();

            Constructor c = null;
            for(Constructor constructor : constructors){
                if(constructor.getParameterTypes()[0].getName().equals("boolean")){
                    c = constructor;
                }
            }

            if(c != null){
                c.setAccessible(true);

                // 初始化参数
                Object parameterObj = c.newInstance(true);

                ByteString requestParam = requestMsg.getRequestParam();
                Method parameterMethod = parameterType.getMethod("parseFrom",ByteString.class);

                // 把方法参数赋值
                parameterObj = parameterMethod.invoke(parameterObj,requestParam);

                response = method.invoke(bean,parameterObj);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }

}

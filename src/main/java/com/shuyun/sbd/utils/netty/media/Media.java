package com.shuyun.sbd.utils.netty.media;

import com.google.protobuf.ByteString;
import com.shuyun.sbd.utils.JsonUtil;
import com.shuyun.sbd.utils.netty.http.bean.RequestParam;
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

    public static Object execute(Object obj){

        Object response = null;

        String cmd = "";

        Object parameterObj = null;

        try {

            if(obj instanceof RequestMsgProbuf.RequestMsg){
                cmd = ((RequestMsgProbuf.RequestMsg)obj).getCmd();
            }else if(obj instanceof RequestParam){
                cmd = ((RequestParam) obj).getCommand();
            }

            MethodBean methodBean = methodBeans.get(cmd);

            Method method = methodBean.getMethod();

            Object bean = methodBean.getBean();

            if(obj instanceof RequestMsgProbuf.RequestMsg){

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
                    parameterObj = c.newInstance(true);

                    ByteString requestParam = ((RequestMsgProbuf.RequestMsg) obj).getRequestParam();

                    Method parameterMethod = parameterType.getMethod("parseFrom",ByteString.class);

                    // 把方法参数赋值
                    parameterObj = parameterMethod.invoke(parameterObj,requestParam);

                }

            }else if(obj instanceof RequestParam){
                RequestParam requestParam = (RequestParam)obj;
                parameterObj = JsonUtil.readValue(requestParam.getParameter().toString(),method.getParameterTypes()[0]);
            }

            // 例：调用controller 的 saveUser 方法
            response = method.invoke(bean,parameterObj);


        }catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }

}

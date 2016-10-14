package com.shuyun.sbd.utils.netty.media;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Component: spring容器初始化成功后执行
 * Description:
 * Date: 16/8/7
 *
 * @author yue.zhang
 */
//@Component
public class InitMedia implements ApplicationListener<ContextRefreshedEvent>,Ordered {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, Object> beanMap = event.getApplicationContext().getBeansWithAnnotation(Controller.class);

        for(String key : beanMap.keySet()){
            Object bean = beanMap.get(key);
            Method [] methods = bean.getClass().getDeclaredMethods();
            if(methods == null || methods.length == 0){
                continue;
            }
            for( Method m : methods){
                if(m.isAnnotationPresent(Remote.class)){
                    Remote remote = m.getAnnotation(Remote.class);
                    String cmd = remote.value();

                    MethodBean methodBean = new MethodBean();
                    methodBean.setBean(bean);
                    methodBean.setMethod(m);
                    Media.methodBeans.put(cmd, methodBean);

                    System.out.println("cmd: " + cmd);
                }
            }
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE; // 比较高的顺序执行
    }
}

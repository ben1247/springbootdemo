package com.shuyun.sbd.annotation.demo1;

import java.lang.reflect.Field;

/**
 * Component: 注解处理器
 * Description:
 * Date: 16/4/11
 *
 * @author yue.zhang
 */
public class FruitInfoUtil {

    public static void getFruitInfo(Class<?> clazz){
        String strFruitName=" 水果名称：";
        String strFruitColor=" 水果颜色：";
        String strFruitProvicer="供应商信息：";

        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(FruitName.class)){
                FruitName fruitName = field.getAnnotation(FruitName.class);
                strFruitName += fruitName.value();
                System.out.println(strFruitName);
            }
            if(field.isAnnotationPresent(FruitColor.class)){
                FruitColor fruitColor = field.getAnnotation(FruitColor.class);
                strFruitColor += fruitColor.value();
                System.out.println(strFruitColor);
            }
            if(field.isAnnotationPresent(FruitProvider.class)){
                FruitProvider fruitProvider = field.getAnnotation(FruitProvider.class);
                strFruitProvicer += " 供应商编号："+fruitProvider.id()+" 供应商名称："+fruitProvider.name()+" 供应商地址："+fruitProvider.address();
                System.out.println(strFruitProvicer);
            }
        }

    }

    public static void main(String args[]){
        FruitInfoUtil.getFruitInfo(Apple.class);
    }

}

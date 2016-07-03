package com.shuyun.sbd.annotation.demo1;

import java.lang.annotation.*;

/**
 * Component: 水果名称注解
 * Description:
 * Date: 16/4/11
 *
 * @author yue.zhang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitName {
    String value() default "";
}

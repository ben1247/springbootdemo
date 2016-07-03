package com.shuyun.sbd.annotation.demo1;

import java.lang.annotation.*;

/**
 * Component: 水果颜色注解
 * Description:
 * Date: 16/4/11
 *
 * @author yue.zhang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {

    /**
     * 颜色枚举
     */
    public enum Color{BULE,RED,GREEN};

    Color value() default Color.RED;

}

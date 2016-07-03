package com.shuyun.sbd.annotation.demo1;

import java.lang.annotation.*;

/**
 * Component: 水果供应者注解
 * Description:
 * Date: 16/4/11
 *
 * @author yue.zhang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitProvider {

    /**
     * 供应商编号
     * @return
     */
    public int id() default -1;

    /**
     * 供应商名称
     * @return
     */
    public String name() default "";

    /**
     * 供应商地址
     * @return
     */
    public String address() default "";

}

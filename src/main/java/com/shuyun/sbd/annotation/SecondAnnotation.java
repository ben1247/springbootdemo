package com.shuyun.sbd.annotation;

import java.lang.annotation.*;

/**
 * Component:
 * Description:
 * Date: 15/10/17
 *
 * @author yue.zhang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SecondAnnotation {
    String name() default "Second Annotation Name";
    String url() default "Second Annotation Url";
}

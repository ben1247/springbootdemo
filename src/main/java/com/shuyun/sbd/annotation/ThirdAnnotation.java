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
@Target(ElementType.FIELD)
@Inherited
public @interface ThirdAnnotation {

    String value() default "Third Annotation";
}

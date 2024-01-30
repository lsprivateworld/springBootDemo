package com.example.springbootdemo.common.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    /**
     * 操作内容
     */
    String value() default "";
}
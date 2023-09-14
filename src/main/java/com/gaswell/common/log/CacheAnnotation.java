package com.gaswell.common.log;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheAnnotation {
    long expire() default 1 * 60 * 1000;
    //缓存标识
    String name() default "";
}

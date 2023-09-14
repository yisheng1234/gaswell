package com.gaswell.common.log;

import java.lang.annotation.*;
//ElementType.ANNOTATION_TYPE 可以作用于注解
//
//  ElementType.CONSTRUCTOR  可以作用于构造器
//
//  ElementType.FIELD  可以作用于属性
//
//  ElementType.LOCAL_VARIABLE  可以作用于局部变量
//
//  ElementType.METHOD  可以作用于方法
//
//  ElementType.PACKAGE  可以作用于包
//
//  ElementType.PARAMETER  可以作用于方法参数
//
//  ElementType.TYPE  可以作用于类型，比如类、接口、枚举


//Type  代表此注解可以放在类上面 METHOD 代表此注解可以放在方法上。,也可以同时加
@Target({ElementType.METHOD})
//RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
//SOURCE 注解只保留在源文件
//
//  CLASS  注解保留到CLASS文件
//
//  RUNTIME 注解在运行期间一直存在
@Retention(RetentionPolicy.RUNTIME)

@Documented
public @interface LogAnnotation {
    /*
     * 注解开发时上面三个注解都要加上。
     * 定义一个 输出日志注解
     * */

    //定义参数
    String module() default "";
    String operator() default "";
}

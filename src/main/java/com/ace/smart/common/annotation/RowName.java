package com.ace.smart.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 作用域
@Retention(RetentionPolicy.RUNTIME) // 可以通过反射来加载注解
public @interface RowName {
    String name();

}

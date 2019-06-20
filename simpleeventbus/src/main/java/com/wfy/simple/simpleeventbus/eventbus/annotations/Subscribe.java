package com.wfy.simple.simpleeventbus.eventbus.annotations;


import com.wfy.simple.simpleeventbus.eventbus.ThreadMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface Subscribe {
    ThreadMode threadMode() default ThreadMode.MAIN;
}

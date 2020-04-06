package com.xd.kobepcommon.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMsg {

    /**
     * 操作名
     */
    String actionName()default "";
}

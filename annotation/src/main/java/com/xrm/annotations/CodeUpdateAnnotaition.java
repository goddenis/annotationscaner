package com.xrm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CodeUpdateAnnotaition {
    String author() default "" ;
    String date()  default "" ;
    String description() default "";
}

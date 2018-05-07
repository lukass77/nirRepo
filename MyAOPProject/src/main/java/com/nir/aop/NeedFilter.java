package com.nir.aop;

import java.lang.annotation.*;

/**
 * User: NirB
 * Date: 3/30/13
 * Time: 2:50 AM
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedFilter {
}

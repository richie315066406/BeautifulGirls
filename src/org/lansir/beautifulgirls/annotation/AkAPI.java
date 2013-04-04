package org.lansir.beautifulgirls.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  
 * @author zhe.yangz 2011-12-29 下午06:45:16
 */
@Retention(RetentionPolicy.RUNTIME)   
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AkAPI {
    public String url() default "noUrl";
}

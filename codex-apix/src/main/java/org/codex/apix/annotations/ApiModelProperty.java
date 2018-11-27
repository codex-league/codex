package org.codex.apix.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModelProperty {


    /**
     * 描述信息
     *
     * @return
     */
    @AliasFor("describe")
    String value() default "";

    /**
     * 描述信息
     *
     * @return
     */
    @AliasFor("value")
    String describe() default "";


    /**
     * 设置分组
     *
     * @return
     */
    Class<?>[] groups() default {};


}

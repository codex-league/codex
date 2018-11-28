package pub.codex.apix.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API Mapping 描述信息
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiOperation {

    /**
     * 描述信息
     *
     * @return
     */
    @AliasFor("describe")
    String value()  default "";

    /**
     * 描述信息
     *
     * @return
     */
    @AliasFor("value")
    String describe() default "";
}

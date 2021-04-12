package pub.codex.common.utils.bean;

import java.lang.annotation.*;

/**
 * Bean Copy 忽略字段注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanCopyIgnore {

}

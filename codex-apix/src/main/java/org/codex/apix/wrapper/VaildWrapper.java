package org.codex.apix.wrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 验证包装器
 */
public interface VaildWrapper {


    /**
     * 获取类型
     *
     * @return
     */
    abstract Class<? extends Annotation> getType();

    /**
     * 返回 Vaild group
     *
     * @param field 返回当前字段的Valid group
     * @return
     */
    abstract Class<?>[] getGroup(Field field);


}

package org.codex.apix.wrapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


/**
 * vaildation 验证注解组件
 */
@Component
public class VaildComponent {


    @Autowired
    private List<VaildWrapper> vaildWrappers;


    /**
     * 查询字段是否存在@NotNull等注解，并且在提供的group组中
     *
     * @param field
     * @param group 提供的group
     * @return
     */
    public boolean matchVaild(Field field, Class<?>... group) {

        long count = vaildWrappers.stream().filter(vaildWrapper -> marchClassArray(group, vaildWrapper.getGroup(field))).count();

        return count > 0;
    }


    /**
     * 尝试寻找字段是否存在 @Notnull等注解
     *
     * @param field
     * @return
     */
    public long findValidAnnotation(Field field) {
        return vaildWrappers.stream().filter(f -> field.getAnnotation(f.getType()) != null).count();

    }


    /**
     * 检查两个数组有没有相同的对象
     *
     * @param aObject
     * @param bObject
     * @return
     */
    private boolean marchClassArray(Object[] aObject, Object[] bObject) {

        if(aObject == null || bObject == null ){
            return false;
        }

        long index = Arrays.stream(aObject).filter(a -> {
            long count = Arrays.stream(bObject).filter(b -> a.equals(b)).count();
            return count > 0;
        }).count();

        return index > 0;
    }

}

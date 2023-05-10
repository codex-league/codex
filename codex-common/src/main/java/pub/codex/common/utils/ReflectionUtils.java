package pub.codex.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    /**
     * 获取指定类及其所有父类中的所有字段（包括私有、保护和公共字段）
     *
     * @param clazz 要获取字段的类
     * @return 字段列表
     */
    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();

        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        return fields.toArray(new Field[0]);
    }
}
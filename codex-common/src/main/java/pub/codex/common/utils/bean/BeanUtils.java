package pub.codex.common.utils.bean;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * Bean操作工具类
 */
public class BeanUtils {


    /**
     * 获取 object 字段 内容
     *
     * @param bean 源对象
     * @param name 字段名称
     * @return
     */
    public static Object getProperty(Object bean, String name) {

        try {
            return PropertyUtils.getProperty(bean, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 设置 object 字段 内容
     *
     * @param bean  源对象
     * @param name  字段名称
     * @param value 设置的内容
     * @return
     */
    public static void setProperty(Object bean, String name, Object value) {

        try {
            PropertyUtils.setProperty(bean, name, value);
        } catch (Exception t) {
            throw new RuntimeException(t);
        }
    }


    /**
     * COPY 2个对象的值, 2对象必须是同一个类
     *
     * @param targetObj:目标对象
     * @param sourceObj:被复制对象
     * @throws Exception
     */
    public static void copyProperty(Object targetObj, Object sourceObj) {

        Class<?> c = sourceObj.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (field.isAnnotationPresent(BeanCopyIgnore.class)) {
                continue;
            }

            try {
                Object valueObject = PropertyUtils.getProperty(sourceObj, fieldName);
                if (valueObject != null || field.isAnnotationPresent(BeanCopyAllowNull.class)) {
                    PropertyUtils.setProperty(targetObj, fieldName, valueObject);
                }
            } catch (IllegalArgumentException ignored) {

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * COPY 第一个对象 中存在属性的值。2对象可以不同类
     *
     * @param targetObj 目标对象
     * @param sourceObj 被复制对象
     * @throws Exception
     */
    public static void copyFixedProperty(Object targetObj, Object sourceObj) {

        copyFixedProperty(targetObj, sourceObj, true);
    }

    /**
     * @param targetObj 目标对象
     * @param sourceObj 源对象
     * @param notIgnore 默认 true
     */
    public static void copyFixedProperty(Object targetObj, Object sourceObj, boolean notIgnore) {

        Field[] sourceFields = sourceObj.getClass().getDeclaredFields();
        Field[] targetFields = targetObj.getClass().getDeclaredFields();

        try {
            for (Field field : sourceFields) {
                String fieldName = field.getName();
                if (field.isAnnotationPresent(BeanCopyIgnore.class)) {
                    continue;
                }
                // 判断是否存在属性
                if (!notIgnore && !hasProperty(targetFields, fieldName)) {
                    continue;
                }

                Object valueObject = PropertyUtils.getProperty(sourceObj, fieldName);
                if (valueObject != null || field.isAnnotationPresent(BeanCopyAllowNull.class)) {
                    PropertyUtils.setProperty(targetObj, fieldName, valueObject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean hasProperty(Field[] fields, String fieldName) {

        for (Field f : fields) {
            if (f.getName().equals(fieldName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 对象转Map
     *
     * @param object
     * @return
     */
    public static Map Bean2Map(Object object) {
        if (object != null) {
            return new org.apache.commons.beanutils.BeanMap(object);
        }
        return null;
    }

    /**
     * Map转对象
     *
     * @param object
     * @param map
     * @return
     * @throws Exception
     */
    public static Object Map2Bean(Object object, Map map) throws Exception {
        if (object != null) {
            org.apache.commons.beanutils.BeanUtils.populate(object, map);
        }
        return object;
    }


    private BeanUtils() {

    }

}

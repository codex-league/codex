package pub.codex.common.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import pub.codex.common.CodexException;
import pub.codex.common.field.ControllerMethod;
import pub.codex.common.field.ControllerlClass;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class BaseUtil {
    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new CodexException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取用户选择需要的接口类型
     *
     * @param controllerlClass
     * @return
     */
    public static Map<String, ControllerMethod> getPropertyByMethod(ControllerlClass controllerlClass) {
        if (controllerlClass == null) {
            return null;
        }
        Map<String, ControllerMethod> map = new HashMap<>();
        Class<?> clazz = controllerlClass.getClass();//获得实体类名
        Field[] fields = controllerlClass.getClass().getDeclaredFields();//获得属性
        //获得Object对象中的所有方法
        for (Field field : fields) {
            try {
                PropertyDescriptor pd = new PropertyDescriptor (( String ) field.getName(),clazz );
                java.lang.reflect.Method method = pd.getReadMethod();//获得get方法
                if (method.invoke(controllerlClass) != null) {
                    map.put(field.getName(), (ControllerMethod) method.invoke(controllerlClass));
                }
            } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
                throw new CodexException(e.toString());
            }
        }
        return map;
    }
}

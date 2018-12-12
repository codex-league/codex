package pub.codex.common.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import pub.codex.common.CodexException;
import pub.codex.common.column.ControllerColumn;
import pub.codex.common.column.FieldColumn;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * @param controllerColumn
     * @return
     */
    public static Map<String, FieldColumn> getMethod(ControllerColumn controllerColumn) {
        if (controllerColumn == null) {
            return null;
        }
        Map<String,FieldColumn> map = new HashMap<>();
        Class clazz = controllerColumn.getClass();//获得实体类名
        Field[] fields = controllerColumn.getClass().getDeclaredFields();//获得属性
        //获得Object对象中的所有方法
        for (Field field : fields) {
            try {
                PropertyDescriptor pd = null;
                pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();//获得get方法
                if (getMethod.invoke(controllerColumn) != null) {
                    map.put(field.getName(),(FieldColumn)getMethod.invoke(controllerColumn));
                }
            } catch (Exception e) {
               throw new CodexException("执行get方法错误！");
            }
        }
        return map;
    }
}

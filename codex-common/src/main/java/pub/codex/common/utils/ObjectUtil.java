package pub.codex.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 对象工具类
 *
 * @author xuxi
 */
public class ObjectUtil {

    public static <T> T toObject(Class<T> clzz, Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(object, clzz);
    }
}

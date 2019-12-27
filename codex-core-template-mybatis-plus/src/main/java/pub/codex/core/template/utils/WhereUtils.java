package pub.codex.core.template.utils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

public class WhereUtils {


    /**
     * @param entity      条件构造器
     * @param jsonWhere   Where条件JSON字符串的形式
     * @param jsonKeyword Keyword条件JSON字符串的形式
     * @return
     */
    public static QueryWrapper setWhereAndKeyword(QueryWrapper entity,
                                                  String jsonWhere,
                                                  String jsonKeyword) {

        WhereUtils.setWhere(entity, jsonWhere);
        WhereUtils.setKeyword(entity, jsonKeyword);
        return entity;
    }


    /**
     * 过滤条件
     * 例如：
     *
     * @param entity    条件构造器
     * @param jsonWhere Where条件JSON字符串的形式
     * @return
     */
    public static QueryWrapper setWhere(QueryWrapper entity, String jsonWhere) {

        if (entity != null && jsonWhere != null) {
            entity.allEq(JSON.parseObject(jsonWhere));
        }
        return entity;
    }


    /**
     * 过滤条件
     * 例如：
     *
     * @param entity      条件构造器
     * @param jsonKeyword Keyword条件JSON字符串的形式
     * @return
     */
    public static QueryWrapper setKeyword(QueryWrapper<?> entity, String jsonKeyword) {


        if (entity != null && jsonKeyword != null) {

            Map<String, Object> keywordMap = JSON.parseObject(jsonKeyword);
            for (String key : keywordMap.keySet()) {
                entity.or().eq(key, keywordMap.get(key));
            }
        }
        return entity;
    }


}

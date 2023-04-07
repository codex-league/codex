package pub.codex.core.template.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;

public class WhereUtils {


    /**
     * @param query      条件构造器
     * @param jsonWhere   Where条件JSON字符串的形式
     * @param jsonKeyword Keyword条件JSON字符串的形式
     * @return
     */
    public static<T> QueryWrapper<T> setWhereAndKeyword(QueryWrapper<T> query,
                                                        String jsonWhere,
                                                        String jsonKeyword) {
        if(StringUtils.isNotBlank(jsonWhere)){
            setWhere(query, jsonWhere);
        }

        if(StringUtils.isNotBlank(jsonKeyword)){
            setKeyword(query, jsonKeyword);
        }

        return query;
    }


    /**
     * 条件构造器
     *
     * @param query
     * @param jsonWhere
     * @return
     * @param <T>
     */
    public static<T> QueryWrapper<T> setWhere(QueryWrapper<T> query, String jsonWhere) {

        JSONObject jsonObj = JSON.parseObject(jsonWhere);
        Set<Map.Entry<String, Object>> params = jsonObj.entrySet();

        for (Map.Entry<String, Object> entry : params) {
            String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entry.getKey());

            if(entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())){
                query.eq(field, entry.getValue());
            }

        }

        return query;
    }


    /**
     * 关键字构造器
     *
     * @param query
     * @param jsonKeyword
     * @return
     * @param <T>
     */
    public static<T> QueryWrapper<T> setKeyword(QueryWrapper<T> query, String jsonKeyword) {

        JSONObject jsonObj = JSON.parseObject(jsonKeyword);
        Set<Map.Entry<String, Object>> params = jsonObj.entrySet();

        for (Map.Entry<String, Object> entry : params) {
            String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entry.getKey());

            if(entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())){
                query.or().eq(field, entry.getValue());
            }
        }

        return query;
    }

}
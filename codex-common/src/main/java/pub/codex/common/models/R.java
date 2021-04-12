package pub.codex.common.models;


import com.fasterxml.jackson.core.type.TypeReference;
import pub.codex.common.exception.RsponseException;
import pub.codex.common.utils.ObjectUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 */
public class R<T> extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static final String CODE = "code";

    public static final String MSG = "msg";

    public static final String DATA = "data";

    public static final String CODE_VALUE = "0000";

    public static final String MSG_VALUE = "successful";

    public static final String ERROR_CODE_VALUE = "9999";

    public static final String ERROR_MSG_VALUE = "unsuccessful";

    public R() {
        this.put(CODE, CODE_VALUE);
        this.put(MSG, MSG_VALUE);
    }

    public R(String code, String msg) {
        this.put(CODE, code);
        this.put(MSG, msg);
    }

    @Override
    public R<T> put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public R<T> data(Object value) {
        super.put(DATA, value);
        return this;
    }

    public R<T> dataMap(Map<String, Object> map) {
        super.putAll(map);
        return this;
    }

    /**
     * 根据类泛型返回数据
     * 如因泛型类型擦除原因，导致无法强制转换,见下
     * eg.
     * java.util.LinkedHashMap cannot be cast to {Object}
     * 请使用  getData(Class<T> clzz) 或 getData(TypeReference<T> toValueTypeRef) 指明转换类型
     *
     * @return
     */
    public T getData() {
        if (!super.get(CODE).equals(CODE_VALUE)) {
            throw new RsponseException(super.get(CODE).toString(), super.get(MSG).toString());
        }

        return (T) super.get(DATA);
    }

    /**
     * 指定返回类型返回数据
     *
     * @param clzz 指定的类型
     * @return
     */
    public T getData(Class<T> clzz) {
        if (!super.get(CODE).equals(CODE_VALUE)) {
            throw new RsponseException(super.get(CODE).toString(), super.get(MSG).toString());
        }

        return ObjectUtil.toObject(clzz, super.get(DATA));
    }

    /**
     * 指定返回类型返回数据
     *
     * @param toValueTypeRef 指定的类型
     * @return
     */
    public T getData(TypeReference<T> toValueTypeRef) {
        if (!super.get(CODE).equals(CODE_VALUE)) {
            throw new RsponseException(super.get(CODE).toString(), super.get(MSG).toString());
        }

        return ObjectUtil.toObject(toValueTypeRef, super.get(DATA));
    }

    public <C> C get(Class<C> clzz, String key) {
        return ObjectUtil.toObject(clzz, super.get(key));
    }

    public <C> C get(TypeReference<C> toValueTypeRef, String key) {
        return ObjectUtil.toObject(toValueTypeRef, super.get(key));
    }


    @Deprecated
    public static R ok(String msg) {
        R r = new R();
        r.put(MSG, msg);
        return r;
    }

    @Deprecated
    public static R error(String code, String msg) {
        R r = new R();
        r.put(CODE, code);
        r.put(MSG, msg);
        return r;
    }

    @Deprecated
    public static R error() {
        return error("9999", "failed");
    }

    @Deprecated
    public static R error(String msg) {
        return error("9999", msg);
    }

    @Deprecated
    public static R<?> ok() {
        return new R<>();
    }

}

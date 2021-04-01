package pub.codex.common.models;


import pub.codex.common.utils.ObjectUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 */
public class R extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    private static final String CODE = "code";

    private static final String MSG = "msg";

    private static final String DATA = "data";

    public R() {
        this.put(CODE, "0000");
        this.put(MSG, "successful");
    }

    public static R error(String code, String msg) {
        R r = new R();
        r.put(CODE, code);
        r.put(MSG, msg);
        return r;
    }

    public static R error() {
        return error("9999", "failed");
    }

    public static R error(String msg) {
        return error("9999", msg);
    }

    public static R ok() {
        return new R();
    }

    public static R ok(String msg) {
        R r = new R();
        r.put(MSG, msg);
        return r;
    }

    public R data(Object value) {
        super.put(DATA, value);
        return this;
    }

    public <T> T getData(Class<T> clzz) {
        return ObjectUtil.toObject(clzz, getData());
    }

    public Object getData() {
        return this.get(DATA);
    }


    public R dataMap(Map<String, Object> map) {
        super.putAll(map);
        return this;
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public <T> T get(Class<T> clzz, String key) {
        return ObjectUtil.toObject(clzz, super.get(key));
    }
}

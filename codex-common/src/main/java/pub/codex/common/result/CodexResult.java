package pub.codex.common.result;


import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 */
public class CodexResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public CodexResult() {
        put("code", 200);
        put("msg", "成功");
    }


    public static CodexResult error(String code, String msg) {
        CodexResult r = new CodexResult();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }


    public static CodexResult error(String msg) {
        return error("500", msg);
    }


    public static CodexResult ok() {
        return new CodexResult();
    }

    public static CodexResult ok(String msg) {
        CodexResult r = new CodexResult();
        r.put("msg", msg);
        return r;
    }

    public static CodexResult ok(Map<String, Object> map) {
        CodexResult r = new CodexResult();
        r.putAll(map);
        return r;
    }


    @Override
    public CodexResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}

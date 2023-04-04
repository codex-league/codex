package pub.codex.common.result;


/**
 * R 构造者
 *
 * @author xuxi
 */
public class RBuilder {

    public static R build(String code, String msg) {
        return new R(code, msg);
    }

    public static <T> RData<T> build(T data) {
        return new RData<>(data);
    }

    public static <T> RData<T> build(T data, String code, String msg) {
        return new RData<>(code, msg, data);
    }

    public static <T> RData<T> build(T data, String code, String msg, Object tips) {
        return new RData<>(code, msg, tips, data);
    }

    public static R ok() {
        return new R();
    }

    public static R error() {
        return new R(R.ERROR_CODE_VALUE, R.ERROR_MSG_VALUE);
    }


    public static R error(String msg) {
        return new R(R.ERROR_CODE_VALUE, msg);
    }

    public static R error(String code, String msg) {
        return new R(code, msg);
    }


    public static R tips(String code, String msg, Object tips) {
        return new R(code, msg, tips);
    }

}

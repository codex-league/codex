package pub.codex.common.result;


import java.util.List;

/**
 * R 构造者
 *
 * @author xuxi
 */
public class RBuilder {

    public static R build(Integer code, String msg) {
        return new R(code, msg);
    }

    public static <T> RPage<T> build(List<T> data, Long pageNum, Long pageSize, Long totalCount) {
        return new RPage<>(data, pageNum, pageSize, totalCount);
    }

    public static <T> RData<T> build(T data) {
        return new RData<>(data);
    }

    public static <T> RData<T> build(T data, Integer code, String msg) {
        return new RData<>(code, msg, data);
    }

    public static <T> RData<T> build(T data, Integer code, String msg, Object tips) {
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

    public static R error(Integer code, String msg) {
        return new R(code, msg);
    }

    public static R tips(Integer code, String msg, Object tips) {
        return new R(code, msg, tips);
    }


}

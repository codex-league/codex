package pub.codex.common.factory;

import pub.codex.common.models.R;

/**
 * R 对象工厂类
 *
 * @author xuxi
 */
public class RFactory {

    public static R<?> build() {
        return new R<>();
    }

    public static R<?> build(String code, String msg) {
        return new R<>(code, msg);
    }

    public static <T> R<T> build(Class<T> clzz) {
        return new R<>();
    }

    public static <T> R<T> build(Class<T> clzz, T data, String code, String msg) {
        R<T> r = new R<>(code, msg);
        r.data(data);
        return r;
    }

    public static <T> R<T> build(Class<T> clzz, T data) {
        R<T> r = new R<>();
        r.data(data);
        return r;
    }

    public static R<?> error() {
        return new R<>(R.ERROR_CODE_VALUE, R.ERROR_MSG_VALUE);
    }

    public static R<?> error(String msg) {
        return new R<>(R.ERROR_CODE_VALUE, msg);
    }
}

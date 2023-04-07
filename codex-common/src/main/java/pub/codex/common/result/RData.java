package pub.codex.common.result;


/**
 * 返回结果，带参数
 */
public class RData<T> extends R {

    /**
     * data
     */
    private T data;

    public RData(T value) {
        this.data = value;
    }

    public RData(Integer code, String msg,T value) {
        super(code, msg);
        this.data = value;
    }

    public RData(Integer code, String msg, Object tips,T value) {
        super(code, msg, tips);
        this.data = value;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

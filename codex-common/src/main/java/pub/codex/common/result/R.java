package pub.codex.common.result;


import java.io.Serializable;

/**
 * 返回数据
 */
public class R implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CODE_VALUE = "0000";

    public static final String MSG_VALUE = "successful";

    public static final String ERROR_CODE_VALUE = "9999";

    public static final String ERROR_MSG_VALUE = "unsuccessful";


    /**
     * message code
     */
    private String code = "0000";

    /**
     * message
     */
    private String msg = "successful";


    /**
     * Development Tips
     */
    private Object tips = "none";

    public R() {
    }

    public R(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(String code, String msg, Object tips) {
        this.code = code;
        this.msg = msg;
        this.tips = tips;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getTips() {
        return tips;
    }

    public void setTips(Object tips) {
        this.tips = tips;
    }
}

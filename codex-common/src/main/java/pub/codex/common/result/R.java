package pub.codex.common.result;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * 返回数据
 */
public class R implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer CODE_VALUE = 200;

    public static final String MSG_VALUE = "successful";

    public static final Integer ERROR_CODE_VALUE = 9999;

    public static final String ERROR_MSG_VALUE = "unsuccessful";


    /**
     * message code
     */
    private Integer code = CODE_VALUE;

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

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(Integer code, String msg, Object tips) {
        this.code = code;
        this.msg = msg;
        this.tips = tips;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

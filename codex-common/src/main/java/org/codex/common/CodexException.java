package org.codex.common;

/**
 * 自定义异常
 */
public class CodexException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String code = "500";
    private String msg;

    public CodexException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CodexException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public CodexException(String msg, String code) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CodexException(String msg, String code, Throwable e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
    }
    

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

package pub.codex.common.exception;

/**
 * @author xuxi
 */

public class RsponseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code;
    private String msg;


    public RsponseException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

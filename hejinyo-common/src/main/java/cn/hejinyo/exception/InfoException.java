package cn.hejinyo.exception;

import cn.hejinyo.consts.StatusCode;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/5 18:46
 * @Description : 自定义，返回消息的异常
 */
public class InfoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_FAILURE_CODE = StatusCode.FAILURE.getCode();
    private static final String DEFAULT_FAILURE_MESSAGE = StatusCode.FAILURE.getMsg();

    private int code = DEFAULT_FAILURE_CODE;


    public InfoException(StatusCode statusCode) {
        super(statusCode.getMsg());
        this.code = statusCode.getCode();
    }

    public InfoException(int code) {
        super(DEFAULT_FAILURE_MESSAGE);
        this.code = code;
    }

    public InfoException(String message) {
        super(message);
    }

    public InfoException(int code, String message) {
        super(message);
        this.code = code;
    }

    public InfoException(Throwable cause) {
        super(cause);
    }

    public InfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfoException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    int getCode() {
        return code;
    }
}

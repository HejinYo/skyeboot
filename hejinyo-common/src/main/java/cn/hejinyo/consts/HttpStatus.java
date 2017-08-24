package cn.hejinyo.consts;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/19 17:29
 * @Description :
 */
public enum HttpStatus {
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "无此操作权限"),
    NOT_FOUND(404, "无此资源"),
    METHOD_NOT_ALLOWED(405, "请求方法被禁止"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体格式"),
    INTERNAL_SERVER_ERROR(500, "服务器发生异常");

    private final int value;
    private final String reasonPhrase;

    private HttpStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}

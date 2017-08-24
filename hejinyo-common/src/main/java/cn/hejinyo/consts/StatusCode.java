package cn.hejinyo.consts;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/5 18:25
 * @Description :
 */
public enum StatusCode {
    FAILURE(-1, "失败"),
    SUCCESS(1, "成功"),
    PARAMETER_ERROR(1001, "提交参数不符合规范"),
    USERTOKEN_PARAMETER_ERROR(1002, "请登录后继续操作"),
    LOGIN_FAILURE(1110, "登录失败"),
    LOGIN_EXCESSIVE_ATTEMPTS(1111, "登录失败次数太多，请30分钟后再试"),
    LOGIN_VERIFYCODE_ERROR(1112, "验证码错误"),
    LOGIN_PASSWORD_ERROR(1113, "用户密码错误"),
    LOGIN_USER_NOEXIST(1114, "用户不存在"),
    LOGIN_USER_LOCK(1115, "此用户已被禁用"),
    TOKEN_OVERDUE(1130, "登录过期，请重新登录");

    private final int code;
    private final String msg;

    private StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

package cn.hejinyo.consts;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/19 18:54
 * @Description :
 */
public enum UserToken {
    USERNAME("use"),//用户名,username
    USERID("uid");//用户id,userid
    private final String value;

    private UserToken(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

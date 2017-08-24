package cn.hejinyo.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/30 16:48
 * @Description :
 */
public class BaseToken implements AuthenticationToken {
    //token类型
    protected String tokenType;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}

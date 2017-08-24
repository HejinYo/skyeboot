package cn.hejinyo.shiro.token;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/29 18:07
 * @Description :
 */
public class StatelessLoginToken extends BaseToken {

    private String username;
    private String password;

    public StatelessLoginToken(final String tokenType, final String username, final String password) {
        super.tokenType = tokenType;
        this.username = username;
        this.password = password;
    }

    public StatelessLoginToken(final String username, final String password) {
        this(StatelessLoginToken.class.getSimpleName(), username, password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Object getPrincipal() {
        return getUsername();
    }

    public Object getCredentials() {
        return getPassword();
    }

    @Override
    public String toString() {
        String sb = getClass().getName() +
                " - " +
                super.tokenType +
                username;
        return sb;
    }
}

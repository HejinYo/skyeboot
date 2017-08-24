package cn.hejinyo.shiro.realm;

import cn.hejinyo.shiro.token.StatelessLoginToken;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import cn.hejinyo.system.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/30 11:48
 * @Description :
 */
public class StatelessLoginTokenRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessLoginToken;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StatelessLoginToken loginToken = (StatelessLoginToken) token;
        String username = loginToken.getUsername();//从Token中获取身份信息
        CurrentUserDTO currentUserDTO = sysUserService.getCurrentUser(username);//根据登录名查询用户信息
        if (null == currentUserDTO || -1 == currentUserDTO.getState()) {// 如果无相关用户或已删除则返回null
            return null;
        } else if (1 == currentUserDTO.getState()) {//是否锁定
            throw new LockedAccountException(); //抛出帐号锁定异常
        }
        //获取用户数据库中密码
        String password = currentUserDTO.getUserPwd();
        String salt = currentUserDTO.getUserSalt();//获取用户盐
        // 返回认证信息由父类AuthenticatingRealm进行认证
        return new SimpleAuthenticationInfo(currentUserDTO, password, ByteSource.Util.bytes(salt), getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

}


package cn.hejinyo.shiro.realm;

import cn.hejinyo.shiro.token.StatelessAuthcToken;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import cn.hejinyo.system.service.SysPermissionService;
import cn.hejinyo.system.service.SysRoleService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/29 18:09
 * @Description :
 */
public class StatelessAuthcTokenRealm extends AuthorizingRealm {

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysPermissionService sysPermissionService;

    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持StatelessAuthcToken类型的Token
        return token instanceof StatelessAuthcToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StatelessAuthcToken statelessToken = (StatelessAuthcToken) token;
        //签证信息
        CurrentUserDTO userDTO = (CurrentUserDTO) statelessToken.getCurrentUser();
        //使用缓存中的userToken和当前验证token进行对比
        return new SimpleAuthenticationInfo(userDTO, userDTO.getUserToken(), getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        CurrentUserDTO CurrentUserDTO = (CurrentUserDTO) principals.getPrimaryPrincipal();
        int userId = CurrentUserDTO.getUserId();
        //获取用户权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获得角色信息
        authorizationInfo.addRoles(sysRoleService.getUserRoleSet(userId));
        //获得权限信息
        authorizationInfo.addStringPermissions(sysPermissionService.getUserPermisSet(userId));
        return authorizationInfo;
    }


}

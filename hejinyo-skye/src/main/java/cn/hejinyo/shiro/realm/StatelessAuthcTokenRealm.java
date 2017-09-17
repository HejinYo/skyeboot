package cn.hejinyo.shiro.realm;

import cn.hejinyo.model.dto.CurrentUserDTO;
import cn.hejinyo.service.SysPermissionService;
import cn.hejinyo.service.SysRoleService;
import cn.hejinyo.shiro.token.StatelessAuthcToken;
import cn.hejinyo.utils.RedisKeys;
import cn.hejinyo.utils.RedisUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/29 18:09
 * @Description :
 */
public class StatelessAuthcTokenRealm extends AuthorizingRealm {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private RedisUtils redisUtils;

    private final static Gson gson = new Gson();

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
        CurrentUserDTO currentUserDTO = (CurrentUserDTO) principals.getPrimaryPrincipal();
        int userId = currentUserDTO.getUserId();
        String username = currentUserDTO.getUserName();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获得角色信息
        String json = redisUtils.get(RedisKeys.getAuthCacheKey(username));
        Set<String> roleSet;
        Set<String> permissionsSet;
        Map<String, Set<String>> list;
        if (null != json) {
            list = gson.fromJson(json, new TypeToken<Map<String, Set<String>>>() {
            }.getType());
            roleSet = list.get("role");
            permissionsSet = list.get("permissions");
        } else {
            roleSet = sysRoleService.getUserRoleSet(userId);
            //获得权限信息
            permissionsSet = sysPermissionService.getUserPermisSet(userId);
            list = new HashMap<>();
            list.put("role", roleSet);
            list.put("permissions", permissionsSet);
            redisUtils.set(RedisKeys.getAuthCacheKey(username), list, 600);
        }
        authorizationInfo.addRoles(roleSet);
        authorizationInfo.addStringPermissions(permissionsSet);
        return authorizationInfo;
    }
}

package cn.hejinyo.service;

import cn.hejinyo.base.BaseService;
import cn.hejinyo.model.SysPermission;
import cn.hejinyo.model.dto.RolePermissionTreeDTO;

import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/17 17:06
 * @Description :
 */
public interface SysPermissionService extends BaseService<SysPermission, Integer> {

    /**
     * 查找用户编号对应的权限编码字符串
     *
     * @param userId
     * @return
     */
    Set<String> getUserPermisSet(int userId);

    /**
     * 权限资源是否存在
     *
     * @return
     */
    boolean isExist(SysPermission sysPermission);

    /**
     * 查找角色编号对应的权限编码字符串
     */
    Set<String> getRolePermissionSet(int roleId);

    /**
     * 获得指定角色的授权树
     */
    RolePermissionTreeDTO getResourcePermissionTree(RolePermissionTreeDTO rolePerm);
}

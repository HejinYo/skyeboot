package cn.hejinyo.service;

import cn.hejinyo.base.BaseService;
import cn.hejinyo.model.SysRole;
import cn.hejinyo.model.dto.RolePermissionTreeDTO;
import cn.hejinyo.model.dto.RoleResourceDTO;
import cn.hejinyo.utils.PageQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/17 17:04
 * @Description :
 */
public interface SysRoleService extends BaseService<SysRole, Integer> {

    /**
     * 查找用户编号对应的角色编码字符串
     *
     * @param userId
     * @return
     */
    Set<String> getUserRoleSet(int userId);

    List<RoleResourceDTO> findPageForRoleResource(PageQuery pageQuery);

    int operationPermission(int roleId, List<RolePermissionTreeDTO> rolePermissionList);

    List<SysRole> roleSelect();
}

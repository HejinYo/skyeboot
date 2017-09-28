package cn.hejinyo.service.impl;

import cn.hejinyo.base.BaseServiceImpl;
import cn.hejinyo.dao.SysPermissionDao;
import cn.hejinyo.model.SysPermission;
import cn.hejinyo.model.dto.RolePermissionTreeDTO;
import cn.hejinyo.service.SysPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/17 17:06
 * @Description :
 */
@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermissionDao, SysPermission, Integer> implements SysPermissionService {

    @Override
    public Set<String> getUserPermisSet(int userId) {
        return baseDao.getUserPermisSet(userId);
    }

    @Override
    public boolean isExist(SysPermission sysPermission) {
        SysPermission permission = new SysPermission();
        permission.setPermCode(sysPermission.getPermCode());
        permission.setResCode(sysPermission.getResCode());
        return baseDao.exsit(permission);
    }

    @Override
    public Set<String> getRolePermissionSet(int roleId) {
        return baseDao.getRolePermissionSet(roleId);
    }

    /**
     * 递归获得资源权限树
     */
    @Override
    public RolePermissionTreeDTO getResourcePermissionTree(RolePermissionTreeDTO rolePerm) {

        List<RolePermissionTreeDTO> resourceList = baseDao.findResourceList(rolePerm.getResId());
        List<RolePermissionTreeDTO> permissionList = baseDao.findPermissionList(rolePerm.getResCode());

        for (RolePermissionTreeDTO permission : permissionList) {
            resourceList.add(0, permission);
        }

        rolePerm.setChildrenRes(resourceList);
        for (int i = permissionList.size(); i < resourceList.size(); i++) {
            getResourcePermissionTree(resourceList.get(i));
        }
        return rolePerm;
    }

}

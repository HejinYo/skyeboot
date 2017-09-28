package cn.hejinyo.service.impl;

import cn.hejinyo.base.BaseServiceImpl;
import cn.hejinyo.dao.SysRoleDao;
import cn.hejinyo.exception.InfoException;
import cn.hejinyo.model.SysRole;
import cn.hejinyo.model.dto.RolePermissionTreeDTO;
import cn.hejinyo.model.dto.RoleResourceDTO;
import cn.hejinyo.service.SysRoleService;
import cn.hejinyo.utils.PageQuery;
import cn.hejinyo.utils.RedisKeys;
import cn.hejinyo.utils.RedisUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/17 17:04
 * @Description :
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRole, Integer> implements SysRoleService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Set<String> getUserRoleSet(int userId) {
        return baseDao.getUserRoleSet(userId);
    }

    @Override
    public List<RoleResourceDTO> findPageForRoleResource(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrder());
        return baseDao.findPageForRoleResource(pageQuery);
    }

    @Override
    public int operationPermission(int roleId, List<RolePermissionTreeDTO> rolePermissionList) {
        //清除redis中的权限缓存
        redisUtils.cleanKey(RedisKeys.getAuthCacheKey("*"));

        //删除原来所有的授权
        baseDao.deleteRolePermission(roleId);
        if (rolePermissionList.size() == 0) {
            return 1;
        }
        //lamuda表达式
        rolePermissionList.removeIf(permissionTree -> permissionTree.getType().equals("resource"));
        if (rolePermissionList.size() > 0) {
            HashMap<String, Object> param = new HashMap<>();
            param.put("roleId", roleId);
            param.put("permission", rolePermissionList);
            return baseDao.saveRolePermission(param);
        }

        throw new InfoException("没有选择任何有效权限");
    }

    @Override
    public List<SysRole> roleSelect() {
        return baseDao.roleSelect();
    }
}

package cn.hejinyo.dao;

import cn.hejinyo.base.BaseDao;
import cn.hejinyo.model.SysRole;
import cn.hejinyo.model.dto.RoleResourceDTO;
import cn.hejinyo.utils.PageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Mapper
public interface SysRoleDao extends BaseDao<SysRole, Integer> {

    /**
     * 查找用户编号对应的角色编码字符串
     *
     * @param userId
     * @return
     */
    Set<String> getUserRoleSet(int userId);

    List<RoleResourceDTO> findPageForRoleResource(PageQuery pageQuery);

    /**
     * 删除角色原来所有权限
     */
    int deleteRolePermission(int roleId);

    /**
     * 保存角色权限
     */
    int saveRolePermission(HashMap<String, Object> param);

    List<SysRole> roleSelect();
}
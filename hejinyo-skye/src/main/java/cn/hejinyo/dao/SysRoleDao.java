package cn.hejinyo.dao;

import cn.hejinyo.base.BaseDao;
import cn.hejinyo.model.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/22 12:44
 * @Description :
 */
@Mapper
public interface SysRoleDao extends BaseDao<SysRole> {

    /**
     * 查找用户编号对应的角色编码字符串
     *
     * @param userId
     * @return
     */
    Set<String> getUserRoleSet(int userId);

}

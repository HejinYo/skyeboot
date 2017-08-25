package cn.hejinyo.dao;

import cn.hejinyo.base.BaseDao;
import cn.hejinyo.model.SysUser;
import cn.hejinyo.model.dto.CurrentUserDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/9 14:59
 * @Description : 用户实体类
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUser> {

    /**
     * 执行登录，查询用户登录信息
     *
     * @param userName
     * @return
     */
    CurrentUserDTO getCurrentUser(String userName);

    /**
     * 用户名是否存在
     *
     * @param userName
     * @return
     */
    int isExistUserName(String userName);

}

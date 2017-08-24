package cn.hejinyo.system.service;

import cn.hejinyo.system.model.SysUser;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import cn.hejinyo.system.model.vo.SysUserVO;
import cn.hejinyo.utils.PageQuery;

import java.util.List;

public interface SysUserService {

    /**
     * 执行登录，查询用户登录信息
     *
     * @param userName
     * @return
     */
    CurrentUserDTO getCurrentUser(String userName);

    /**
     * 获得用户编号对应的用户信息
     *
     * @param userID
     * @return
     */
    SysUserVO get(int userID);

    /**
     * 分页查询用户
     *
     * @return
     */
    List<SysUser> listPage(PageQuery pageQuery);

    /**
     * 增加用户
     *
     * @param userVO
     */
    int save(SysUserVO userVO);

    /**
     * 用户名是否存在
     *
     * @param userName
     * @return
     */
    int isExistUserName(String userName);

    /**
     * 修改用户
     *
     * @param userVO
     * @return
     */
    int update(SysUserVO userVO);

    /**
     * 删除用户
     *
     * @param userVO
     * @return
     */
    int delete(SysUserVO userVO);

    /**
     * 更新用户登录信息
     *
     * @param userDTO
     * @return
     */
    int updateUserLoginInfo(CurrentUserDTO userDTO);
}

package cn.hejinyo.service;

import cn.hejinyo.model.dto.UserMenuDTO;

import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/22 15:10
 * @Description :
 */
public interface SysResourceService {

    /**
     * 查询用户编号可用菜单
     *
     * @param userId
     * @return
     */
    List<UserMenuDTO> getUserMenuList(int userId);
}

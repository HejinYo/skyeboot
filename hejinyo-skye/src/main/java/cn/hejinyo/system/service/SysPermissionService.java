package cn.hejinyo.system.service;

import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/17 17:06
 * @Description :
 */
public interface SysPermissionService {

    /**
     * 查找用户编号对应的权限编码字符串
     *
     * @param userId
     * @return
     */
    Set<String> getUserPermisSet(int userId);
}

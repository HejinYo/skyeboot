package cn.hejinyo.system.service.impl;

import cn.hejinyo.system.dao.SysPermissionDao;
import cn.hejinyo.system.service.SysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/17 17:06
 * @Description :
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    private SysPermissionDao sysPermissionDao;

    @Override
    public Set<String> getUserPermisSet(int userId) {
        return sysPermissionDao.getUserPermisSet(userId);
    }
}

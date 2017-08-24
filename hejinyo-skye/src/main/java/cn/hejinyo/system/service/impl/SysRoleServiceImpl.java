package cn.hejinyo.system.service.impl;

import cn.hejinyo.system.dao.SysRoleDao;
import cn.hejinyo.system.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/17 17:04
 * @Description :
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public Set<String> getUserRoleSet(int userId) {
        return sysRoleDao.getUserRoleSet(userId);
    }
}

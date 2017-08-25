package cn.hejinyo.service.impl;

import cn.hejinyo.dao.SysRoleDao;
import cn.hejinyo.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/17 17:04
 * @Description :
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    public Set<String> getUserRoleSet(int userId) {
        return sysRoleDao.getUserRoleSet(userId);
    }
}

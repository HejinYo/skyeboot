package cn.hejinyo.system.service.impl;

import cn.hejinyo.system.dao.SysResourceDao;
import cn.hejinyo.system.model.dto.UserMenuDTO;
import cn.hejinyo.system.service.SysResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/22 15:11
 * @Description :
 */
@Service
public class SysResourceServiceImpl implements SysResourceService {

    @Resource
    private SysResourceDao sysResourceDao;

    @Override
    public List<UserMenuDTO> getUserMenuList(int userId) {
        return sysResourceDao.getUserMenuList(userId);
    }
}

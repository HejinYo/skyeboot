package cn.hejinyo.service.impl;

import cn.hejinyo.dao.SysResourceDao;
import cn.hejinyo.model.dto.UserMenuDTO;
import cn.hejinyo.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/22 15:11
 * @Description :
 */
@Service
public class SysResourceServiceImpl implements SysResourceService {

    @Autowired
    private SysResourceDao sysResourceDao;

    @Override
    public List<UserMenuDTO> getUserMenuList(int userId) {
        return sysResourceDao.getUserMenuList(userId);
    }
}

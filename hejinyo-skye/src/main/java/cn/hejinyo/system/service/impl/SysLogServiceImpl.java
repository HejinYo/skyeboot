package cn.hejinyo.system.service.impl;

import cn.hejinyo.system.model.SysLog;
import cn.hejinyo.system.dao.SysLogDao;
import cn.hejinyo.system.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/12 22:31
 * @Description :
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public void save(SysLog sysLog) {
        sysLogDao.save(sysLog);
    }

    @Override
    public List<SysLog> list() {
        return sysLogDao.listPage("");
    }
}

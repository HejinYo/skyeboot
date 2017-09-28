package cn.hejinyo.service.impl;

import cn.hejinyo.base.BaseServiceImpl;
import cn.hejinyo.dao.SysResourceDao;
import cn.hejinyo.exception.InfoException;
import cn.hejinyo.model.SysResource;
import cn.hejinyo.model.dto.ResourceTreeDTO;
import cn.hejinyo.model.dto.UserMenuDTO;
import cn.hejinyo.service.SysResourceService;
import cn.hejinyo.utils.ShiroUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/22 15:11
 * @Description :
 */
@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResourceDao, SysResource, Integer> implements SysResourceService {

    @Override
    public List<UserMenuDTO> getUserMenuList(int userId) {
        return baseDao.getUserMenuList(userId);
    }

    @Override
    public List<ResourceTreeDTO> getRecursionTree() {
        List<ResourceTreeDTO> list = baseDao.getRecursionTree();
        ResourceTreeDTO sysResourceTree = new ResourceTreeDTO();
        sysResourceTree.setResId(0);
        sysResourceTree.setResName("/root");
        sysResourceTree.setChildrenRes(list);
        List<ResourceTreeDTO> listtree = new ArrayList<>();
        listtree.add(sysResourceTree);
        return listtree;
    }

    @Override
    public boolean isExistResCode(String resCode) {
        //查询resCode是否存在
        SysResource sysResource = new SysResource();
        sysResource.setResCode(resCode);
        return baseDao.exsit(sysResource);
    }

    @Override
    public int save(SysResource sysResource) {
        SysResource newResource = new SysResource();
        baseDao.updateAdditionSeq(sysResource);
        newResource.setResPid(sysResource.getResPid());
        newResource.setResType(sysResource.getResType());
        newResource.setResName(sysResource.getResName());
        newResource.setResCode(sysResource.getResCode());
        newResource.setResIcon(sysResource.getResIcon());
        newResource.setCreateTime(new Date());
        newResource.setSeq(sysResource.getSeq());
        newResource.setCreateId(ShiroUtils.getUserId());
        newResource.setState(sysResource.getState());
        newResource.setResLevel(sysResource.getResLevel());
        return super.save(newResource);
    }

    @Override
    public int update(SysResource sysResource) {
        int resid = sysResource.getResId();
        int resPid = sysResource.getResPid();
        SysResource oldResource = findOne(resid);
        if (null == oldResource) {
            throw new InfoException("资源不不存在");
        }
        if (resPid == resid) {
            throw new InfoException("不能选择自己作为上级资源");
        }
        SysResource newResource = new SysResource();
        newResource.setResId(resid);
        newResource.setResType(sysResource.getResType());
        newResource.setResCode(sysResource.getResCode());
        newResource.setResName(sysResource.getResName());
        newResource.setResPid(resPid);
        newResource.setResIcon(sysResource.getResIcon());
        newResource.setSeq(sysResource.getSeq());
        newResource.setState(sysResource.getState());
        newResource.setCreateTime(sysResource.getCreateTime());
        newResource.setResLevel(sysResource.getResLevel());
        if (resPid != oldResource.getResPid()) {
            //上级资源改变，原上级资源seq减修改
            baseDao.updateSubtractionSeq(oldResource);
            //新的上级资源seq加修改
            baseDao.updateAdditionSeq(newResource);
            //递归修改子资源级别
            updateChildLevel(newResource.getResId(), newResource.getResLevel());
        } else {
            if (!sysResource.getSeq().equals(oldResource.getSeq())) {
                //原上级资源seq减修改
                baseDao.updateSubtractionSeq(oldResource);
                //原上级资源seq加修改
                baseDao.updateAdditionSeq(newResource);
            }
        }
        return super.update(newResource);
    }

    @Override
    public int delete(Integer resId) {
        int result = super.delete(resId);
        if (result > 0) {
            baseDao.updateSubtractionSeq(findOne(resId));
        }
        return result;
    }

    //递归修改子资源级别
    private void updateChildLevel(int resId, int level) {
        //查询子资源
        SysResource resource = new SysResource();
        resource.setResPid(resId);
        List<SysResource> childRes = baseDao.findList(resource);
        for (SysResource s : childRes) {
            s.setResLevel(level + 1);
            baseDao.update(s);
            updateChildLevel(s.getResId(), s.getResLevel());
        }
    }

}

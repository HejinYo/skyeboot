package cn.hejinyo.dao;

import cn.hejinyo.base.BaseDao;
import cn.hejinyo.model.SysResource;
import cn.hejinyo.model.dto.ResourceTreeDTO;
import cn.hejinyo.model.dto.UserMenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysResourceDao extends BaseDao<SysResource, Integer> {

    /**
     * 查询用户编号可用菜单
     *
     * @param userId
     */
    List<UserMenuDTO> getUserMenuList(int userId);

    /**
     * 递归获得所有资源树
     */
    List<ResourceTreeDTO> getRecursionTree();

    /**
     * 序号加操作
     *
     * @param sysResource
     */
    int updateAdditionSeq(SysResource sysResource);

    /**
     * 序号减操作
     *
     * @param sysResource
     */
    int updateSubtractionSeq(SysResource sysResource);
}
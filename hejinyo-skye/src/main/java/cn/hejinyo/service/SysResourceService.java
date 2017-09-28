package cn.hejinyo.service;

import cn.hejinyo.base.BaseService;
import cn.hejinyo.model.SysResource;
import cn.hejinyo.model.dto.ResourceTreeDTO;
import cn.hejinyo.model.dto.UserMenuDTO;

import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/22 15:10
 * @Description :
 */
public interface SysResourceService extends BaseService<SysResource, Integer> {

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
     * 资源编码是否存在
     *
     * @param resCode
     */
    boolean isExistResCode(String resCode);

}

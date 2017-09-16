package cn.hejinyo.dao;

import cn.hejinyo.annotation.BaseDao;
import cn.hejinyo.model.SysResource;
import cn.hejinyo.model.dto.UserMenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/22 14:08
 * @Description :
 */
@Mapper
public interface SysResourceDao extends BaseDao<SysResource> {

    /**
     * 查询用户编号可用菜单
     *
     * @param userId
     * @return
     */
    List<UserMenuDTO> getUserMenuList(int userId);

}

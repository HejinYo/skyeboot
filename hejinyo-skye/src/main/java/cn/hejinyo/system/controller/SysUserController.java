package cn.hejinyo.system.controller;

import cn.hejinyo.system.model.SysUser;
import cn.hejinyo.system.model.vo.SysUserVO;
import cn.hejinyo.system.service.SysUserService;
import cn.hejinyo.utils.*;
import cn.hejinyo.validator.group.SaveGroup;
import cn.hejinyo.validator.group.UpdateGroup;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/17 22:29
 * @Description :
 */
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获得一个用户信息
     *
     * @return
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Result get(@PathVariable(value = "userId") int userId) {
        SysUserVO userVO = sysUserService.get(userId);
        if (userVO == null) {
            return Result.error("用户不存在");
        }
        return Result.ok(userVO);
    }

    /**
     * 分页查询用户信息
     *
     * @return
     */
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public Result list(PageQuery pageQuery, SysUserVO userVO, @RequestParam(required = false) int startIndex, @RequestParam(required = false) int pageSize) {
        pageQuery.setQuery(userVO);
        SysUser s1 = new SysUser();
        if (StringUtils.isNotEmpty(pageQuery.getSidx())) {
            String sidx = pageQuery.getSidx();
            String json = "{'" + sidx + "':'" + 0 + "'}";
            s1 = JsonUtils.toObject(json, SysUser.class);
        }
        System.out.println(s1);
        pageQuery.setOrd(s1);
        System.out.println(pageQuery);
        //PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        //PageHelper.offsetPage(startIndex, pageSize);
        PageHelper.startPage(startIndex, pageSize);
        List<SysUser> sysUserList = sysUserService.listPage(pageQuery);
        PageInfo<SysUser> userPageInfo = new PageInfo<>(sysUserList, 3);
        return Result.ok(userPageInfo);
    }

    /**
     * 分页查询用户信息
     *
     * @return
     */
    @RequestMapping(value = "/listPage/jqgrid", method = RequestMethod.GET)
    public Result jqgrid(PageQuery pageQuery, SysUserVO userVO, @RequestParam int page, @RequestParam int limit) {
        pageQuery.setQuery(userVO);
        SysUser s1 = new SysUser();
        if (StringUtils.isNotEmpty(pageQuery.getSidx())) {
            String sidx = pageQuery.getSidx();
            String json = "{'" + sidx + "':'" + 0 + "'}";
            s1 = JsonUtils.toObject(json, SysUser.class);
        }
        System.out.println(s1);
        pageQuery.setOrd(s1);
        System.out.println(pageQuery);
        PageHelper.startPage(page, limit);
        List<SysUser> sysUserList = sysUserService.listPage(pageQuery);
        PageInfo<SysUser> userPageInfo = new PageInfo<>(sysUserList, 3);
        ;
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", sysUserList);
        map.put("total", userPageInfo.getTotal());
        map.put("records", 1000);
        map.put("page", page);
        return Result.ok(userPageInfo);
    }

    /**
     * 增加一个用户
     */
    //@RequiresPermissions("user:create")
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody SysUserVO userVO) {
        String validate = ValidatorUtils.validate(userVO, SaveGroup.class);
        if (StringUtils.isNotEmpty(validate)) {
            return Result.error(validate);
        }
        if (sysUserService.isExistUserName(userVO.getUserName()) > 0) {
            return Result.error("用户名已经存在");
        }
        int result = sysUserService.save(userVO);
        if (result == 0) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 用户名是否已经存在
     */
    // @RequiresPermissions("user:create,update")
    @RequestMapping(value = "/isExistUserName/{userName}", method = RequestMethod.GET)
    public Result isExistUserName(@PathVariable("userName") String userName) {
        if (sysUserService.isExistUserName(userName) > 0) {
            return Result.error("用户名已经存在");
        }
        return Result.ok();
    }

    /**
     * 更新一个用户
     *
     * @param userVO
     * @return
     */
    //@RequiresPermissions("user:update")
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public Result update(@RequestBody SysUserVO userVO, @PathVariable("userId") int userId) {
        String validate = ValidatorUtils.validate(userVO, UpdateGroup.class);
        if (StringUtils.isNotEmpty(validate)) {
            return Result.error(validate);
        }
        userVO.setUserId(userId);
        int result = sysUserService.update(userVO);

        if (result > 0) {
            return Result.ok();
        }
        if (result == -1) {
            return Result.error("用户不存在");
        }
        if (result == -2) {
            return Result.error("用户名已经存在");
        }
        if (result == -3) {
            return Result.error("未作任何修改");
        }
        return Result.error();
    }

    /**
     * 删除一个用户
     *
     * @param userId
     * @return
     */
    //@RequiresPermissions("user:delete")
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("userId") int userId) {
        SysUserVO userVO = sysUserService.get(userId);
        if (userVO == null) {
            return Result.error("用户不存在");
        }
        int result = sysUserService.delete(userVO);
        if (result > 0) {
            return Result.ok("删除成功");
        }
        return Result.error("删除失败");
    }
}

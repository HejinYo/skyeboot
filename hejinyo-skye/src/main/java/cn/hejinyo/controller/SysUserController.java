package cn.hejinyo.controller;

import cn.hejinyo.annotation.SysLogger;
import cn.hejinyo.model.SysUser;
import cn.hejinyo.service.SysUserService;
import cn.hejinyo.utils.*;
import cn.hejinyo.validator.RestfulValid;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    @GetMapping(value = "/{userId}")
    public Result get(@PathVariable(value = "userId") int userId) {
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            return Result.error("用户不存在");
        }
        return Result.ok(sysUser);
    }

    /**
     * 分页查询用户信息
     *
     * @return
     */
    @GetMapping(value = "/listPage")
    public Result list(PageQuery pageQuery, SysUser sysUser, @RequestParam(required = false) int startIndex, @RequestParam(required = false) int pageSize) {
        pageQuery.setQuery(sysUser);
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
    @GetMapping(value = "/listPage/jqgrid")
    public Result jqgrid(PageQuery pageQuery, SysUser sysUser, @RequestParam int page, @RequestParam int limit) {
        pageQuery.setQuery(sysUser);
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
    @PostMapping
    public Result save(@Validated(RestfulValid.POST.class) @RequestBody SysUser sysUser) {
        if (sysUserService.isExistUserName(sysUser.getUserName()) > 0) {
            return Result.error("用户名已经存在");
        }
        int result = sysUserService.save(sysUser);
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
     * @param sysUser
     * @return
     */
    //@RequiresPermissions("user:update")
    @SysLogger("更新用户")
    @PutMapping(value = "/{userId}")
    public Result update(@Validated(RestfulValid.PUT.class) @RequestBody SysUser sysUser, @PathVariable("userId") int userId) {
        sysUser.setUserId(userId);
        int result = sysUserService.update(sysUser);
        if (result > 0) {
            return Result.ok();
        }
        return Result.error("未作任何修改");
    }

    /**
     * 删除一个用户
     *
     * @param userId
     * @return
     */
    //@RequiresPermissions("user:delete")
    @SysLogger("删除用户")
    @DeleteMapping(value = "/{userId}")
    public Result delete(@PathVariable("userId") int userId) {
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null) {
            return Result.error("用户不存在");
        }
        int result = sysUserService.delete(sysUser);
        if (result > 0) {
            return Result.ok("删除成功");
        }
        return Result.error("删除失败");
    }
}

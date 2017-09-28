package cn.hejinyo.controller;

import cn.hejinyo.model.SysPermission;
import cn.hejinyo.model.dto.RolePermissionTreeDTO;
import cn.hejinyo.service.SysPermissionService;
import cn.hejinyo.utils.PageInfo;
import cn.hejinyo.utils.PageQuery;
import cn.hejinyo.utils.Result;
import cn.hejinyo.validator.RestfulValid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author : HejinYo   hejinyo@gmail.com     2017/9/17 16:46
 * @apiNote :
 */
@RestController
@RequestMapping("/permission")
public class SysPermissionController extends BaseController {
    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 分页查询
     */
    @GetMapping(value = "/listPage")
    @RequiresPermissions("resource:view")
    public Result list(@RequestParam HashMap<String, Object> paramers) {
        PageInfo<SysPermission> userPageInfo = new PageInfo<>(sysPermissionService.findPage(new PageQuery(paramers)));
        return Result.ok(userPageInfo);
    }

    @GetMapping(value = "/{permId}")
    @RequiresPermissions("resource:view")
    public Result get(@PathVariable(value = "permId") int permId) {
        SysPermission sysPermission = sysPermissionService.findOne(permId);
        if (sysPermission == null) {
            return Result.error("资源权限不存在");
        }
        return Result.ok(sysPermission);
    }


    /**
     * 增加
     */
    @PostMapping
    @RequiresPermissions("resource:create")
    public Result save(@Validated(RestfulValid.POST.class) @RequestBody SysPermission sysPermission) {
        if (sysPermissionService.isExist(sysPermission)) {
            return Result.error("资源权限已经存在");
        }
        int result = sysPermissionService.save(sysPermission);
        if (result == 0) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 更新
     */
    @PutMapping(value = "/{permId}")
    @RequiresPermissions("resource:update")
    public Result update(@Validated(RestfulValid.PUT.class) @RequestBody SysPermission sysPermission, @PathVariable("permId") int permId) {
        sysPermission.setPermId(permId);
        int result = sysPermissionService.update(sysPermission);
        if (result > 0) {
            return Result.ok();
        }
        return Result.error("未作任何修改");
    }

    /**
     * 删除
     */
    @DeleteMapping(value = "/{permId}")
    @RequiresPermissions("resource:delete")
    public Result delete(@PathVariable("permId") int permId) {
        SysPermission sysPermission = sysPermissionService.findOne(permId);
        if (sysPermission == null) {
            return Result.error("资源权限不存在");
        }
        int result = sysPermissionService.delete(sysPermission.getPermId());
        if (result > 0) {
            return Result.ok("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 获得角色已授权权限
     */
    @GetMapping("/granted/{roleId}")
    public Result grantedPermission(@PathVariable("roleId") int roleId) {
        return Result.ok(sysPermissionService.getRolePermissionSet(roleId));
    }

    /**
     * 权限树
     */
    @GetMapping("/tree")
    public Result permissonTree() {
        RolePermissionTreeDTO res = new RolePermissionTreeDTO();
        res.setResId(0);
        res.setResCode("");
        return Result.ok(sysPermissionService.getResourcePermissionTree(res));
    }


}

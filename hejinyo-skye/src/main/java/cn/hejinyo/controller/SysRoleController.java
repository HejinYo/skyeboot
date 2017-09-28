package cn.hejinyo.controller;

import cn.hejinyo.model.SysRole;
import cn.hejinyo.model.dto.RolePermissionTreeDTO;
import cn.hejinyo.model.dto.RoleResourceDTO;
import cn.hejinyo.service.SysRoleService;
import cn.hejinyo.utils.PageInfo;
import cn.hejinyo.utils.PageQuery;
import cn.hejinyo.utils.Result;
import cn.hejinyo.validator.RestfulValid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com     2017/9/27 19:03
 * @apiNote :
 */
@RestController
@RequestMapping("/role")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping(value = "/listPage")
    public Result list(@RequestParam HashMap<String, Object> paramers) {
        PageInfo<SysRole> rolePageInfo = new PageInfo<>(sysRoleService.findPage(new PageQuery(paramers)));
        return Result.ok(rolePageInfo);
    }

    @GetMapping(value = "/roleResourceListPage")
    public Result roleResourceList(@RequestParam HashMap<String, Object> paramers) {
        PageInfo<RoleResourceDTO> roleResourcePageInfo = new PageInfo<>(sysRoleService.findPageForRoleResource(new PageQuery(paramers)));
        return Result.ok(roleResourcePageInfo);
    }

    /**
     * 增加
     */
    @PostMapping
    @RequiresPermissions("role:create")
    public Result save(@Validated(RestfulValid.POST.class) @RequestBody SysRole SysRole) {
        int result = sysRoleService.save(SysRole);
        if (result == 0) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 更新
     */
    @PutMapping(value = "/{roleId}")
    @RequiresPermissions("role:update")
    public Result update(@Validated(RestfulValid.PUT.class) @RequestBody SysRole SysRole, @PathVariable("roleId") Integer roleId) {
        SysRole.setRoleId(roleId);
        int result = sysRoleService.update(SysRole);
        if (result > 0) {
            return Result.ok();
        }
        return Result.error("未作任何修改");
    }


    /**
     * 删除
     */
    @DeleteMapping(value = "/{roleId}")
    @RequiresPermissions("role:delete")
    public Result delete(@PathVariable("roleId") Integer roleId) {
        SysRole SysRole = sysRoleService.findOne(roleId);
        if (SysRole == null) {
            return Result.error("资源不存在");
        }
        int result = sysRoleService.delete(SysRole.getRoleId());
        if (result > 0) {
            return Result.ok("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 授权
     */
    @PostMapping(value = "/authorization/{roleId}")
    @Transactional
    @RequiresPermissions("role:auth")
    public Result operationPermission(@PathVariable("roleId") Integer roleId, @RequestBody List<RolePermissionTreeDTO> rolePermissionList) {
        int result = sysRoleService.operationPermission(roleId, rolePermissionList);
        //新增加授权
        if (result > 0) {
            return Result.ok("授权成功");
        }
        return Result.error("没有修改任何权限");
    }

    /**
     * 角色列表select
     */
    @GetMapping(value = "/select")
    public Result roleSelect() {
        return Result.ok(sysRoleService.roleSelect());
    }

}

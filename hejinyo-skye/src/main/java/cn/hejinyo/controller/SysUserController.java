package cn.hejinyo.controller;

import cn.hejinyo.annotation.SysLogger;
import cn.hejinyo.cloudstorage.CloudStorageConfig;
import cn.hejinyo.cloudstorage.QiniuCloudStorageService;
import cn.hejinyo.model.SysUser;
import cn.hejinyo.service.SysUserService;
import cn.hejinyo.utils.PageInfo;
import cn.hejinyo.utils.PageQuery;
import cn.hejinyo.utils.Result;
import cn.hejinyo.validator.RestfulValid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

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
        SysUser sysUser = sysUserService.findOne(userId);
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
    public Result list(@RequestParam HashMap<String, Object> paramers) {
        PageInfo<SysUser> userPageInfo = new PageInfo<>(sysUserService.findPage(new PageQuery(paramers)));
        return Result.ok(userPageInfo);
    }

    /**
     * 增加一个用户
     */
    @PostMapping
    public Result save(@Validated(RestfulValid.POST.class) @RequestBody SysUser sysUser) {
        System.out.println(sysUser);
        if (sysUserService.isExistUserName(sysUser.getUserName())) {
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
    @RequestMapping(value = "/isExistUserName/{userName}", method = RequestMethod.GET)
    public Result isExistUserName(@PathVariable("userName") String userName) {
        if (sysUserService.isExistUserName(userName)) {
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
    @SysLogger("更新用户")
    @RequiresPermissions("user:update")
    @PutMapping(value = "/{userId}")
    public Result update(@Validated(RestfulValid.PUT.class) @RequestBody SysUser sysUser, @PathVariable("userId") int userId) {
        if (1 == userId) {
            return Result.error("admin不允许修改");
        }
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
    @SysLogger("删除用户")
    @RequiresPermissions("user:delete")
    @DeleteMapping(value = "/{userId}")
    public Result delete(@PathVariable("userId") int userId) {
        if (1 == userId) {
            return Result.error("admin不允许被删除");
        }
        SysUser sysUser = sysUserService.findOne(userId);
        if (sysUser == null) {
            return Result.error("用户不存在");
        }
        int result = sysUserService.delete(sysUser.getUserId());
        if (result > 0) {
            return Result.ok("删除成功");
        }
        return Result.error("删除失败");
    }

    @PostMapping(value = "/qiniuUpload")
    public Result uploadUserAvatar(@RequestParam("file") MultipartFile multipartFile) {
        CloudStorageConfig config = new CloudStorageConfig();
        config.setQiniuAccessKey("GqZQG6TvEZGPkCXzm5O7QN1jipLdeI4CXXsR6N3G");
        config.setQiniuSecretKey("qodIX8q2zqaX4eSAiOvcS1YNLeKU_cxyNtSFkWf9");
        config.setQiniuBucketName("skye-user-avatar");
        config.setKey(getCurrentUser().getUserName());
        QiniuCloudStorageService storageService = new QiniuCloudStorageService(config);
        try {
            storageService.upload(multipartFile.getInputStream(), getCurrentUser().getUserName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    /**
     * 批量删除
     *
     * @return
     */
    //@RequiresPermissions("user:delete")
    /*@SysLogger("删除用户")
    @DeleteMapping(value = "/")
    public Result delete(List<SysUser> sysUser) {
        int result = sysUserService.delete(sysUser);
        if (result > 0) {
            return Result.ok("批量删除成功");
        }
        return Result.error("批量删除失败");
    }*/
}

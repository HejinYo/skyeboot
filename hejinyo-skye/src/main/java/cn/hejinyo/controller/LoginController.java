package cn.hejinyo.controller;

import cn.hejinyo.cloudstorage.CloudStorageConfig;
import cn.hejinyo.service.SysUserService;
import cn.hejinyo.shiro.token.StatelessLoginToken;
import cn.hejinyo.consts.StatusCode;
import cn.hejinyo.model.dto.CurrentUserDTO;
import cn.hejinyo.utils.RedisUtils;
import cn.hejinyo.utils.RedisKeys;
import cn.hejinyo.utils.Result;
import cn.hejinyo.utils.Tools;
import cn.hejinyo.utils.WebUtils;
import com.qiniu.util.Auth;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/")
public class LoginController extends BaseController {
    private static final String DEFAULT_TOKEN_CACHENAME = "tokenCache";

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SysUserService sysUserService;


    /**
     * 执行登录,返回userToken
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody CurrentUserDTO loginUser, HttpServletRequest request) {
        try {
            StatelessLoginToken loginToken = new StatelessLoginToken(loginUser.getUserName(), loginUser.getUserPwd());
            //委托给Realm进行登录
            SecurityUtils.getSubject().login(loginToken);
            CurrentUserDTO userDTO = (CurrentUserDTO) SecurityUtils.getSubject().getPrincipal();
            //创建jwt token
            String token = Tools.createToken(12, userDTO.getUserId(), userDTO.getUserName(), userDTO.getUserPwd());
            userDTO.setUserToken(token);
            userDTO.setLoginIp(WebUtils.getIpAddr(request));
            redisUtils.set(RedisKeys.getShiroCacheKey(DEFAULT_TOKEN_CACHENAME + ":" + userDTO.getUserName()), userDTO, 1800);
            redisUtils.delete(getRoleCacheKey(userDTO.getUserName()));
            redisUtils.delete(getPermissionCacheKey(userDTO.getUserName()));
            sysUserService.updateUserLoginInfo(userDTO);
            return Result.ok(StatusCode.SUCCESS, userDTO);
        } catch (Exception e) {
            //登录失败
            logger.error("[" + loginUser.getUserName() + "] 登录失败：", e.getMessage());
            if (e instanceof UnknownAccountException) {
                return Result.error(StatusCode.LOGIN_USER_NOEXIST);
            }
            if (e instanceof IncorrectCredentialsException) {
                return Result.error(StatusCode.LOGIN_PASSWORD_ERROR);
            }
            if (e instanceof ExcessiveAttemptsException) {
                return Result.error(StatusCode.LOGIN_EXCESSIVE_ATTEMPTS);
            }
            if (e instanceof LockedAccountException) {
                return Result.error(StatusCode.LOGIN_USER_LOCK);
            }
            return Result.error(StatusCode.LOGIN_FAILURE);
        }
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public Result logout() {
        return Result.ok();
    }


    /**
     * 文件上传
     */
    @GetMapping(value = "/fileUploadToken")
    public Result uploadUserAvatar() {
        CloudStorageConfig config = new CloudStorageConfig();
        config.setQiniuAccessKey("GqZQG6TvEZGPkCXzm5O7QN1jipLdeI4CXXsR6N3G");
        config.setQiniuSecretKey("qodIX8q2zqaX4eSAiOvcS1YNLeKU_cxyNtSFkWf9");
        config.setQiniuBucketName("skye-user-avatar");
        String token = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey()).uploadToken(config.getQiniuBucketName());
        return Result.ok("获取成功", token);
    }

    private String getRoleCacheKey(String name) {
        return RedisKeys.getShiroCacheKey("authCache" + ":" + name + ":roles");
    }

    private String getPermissionCacheKey(String name) {
        return RedisKeys.getShiroCacheKey("authCache" + ":" + name + ":permissions");
    }
}

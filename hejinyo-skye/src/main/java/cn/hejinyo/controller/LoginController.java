package cn.hejinyo.controller;

import cn.hejinyo.service.SysUserService;
import cn.hejinyo.shiro.token.StatelessLoginToken;
import cn.hejinyo.consts.StatusCode;
import cn.hejinyo.model.dto.CurrentUserDTO;
import cn.hejinyo.utils.Result;
import cn.hejinyo.utils.Tools;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/")
public class LoginController extends BaseController {
    private static final String DEFAULT_TOKEN_CACHENAME = "tokenCache";

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private SysUserService sysUserService;


    /**
     * 执行登录,返回userToken
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody CurrentUserDTO loginUser) {
        try {
            StatelessLoginToken loginToken = new StatelessLoginToken(loginUser.getUserName(), loginUser.getUserPwd());
            //委托给Realm进行登录
            SecurityUtils.getSubject().login(loginToken);
            CurrentUserDTO userDTO = (CurrentUserDTO) SecurityUtils.getSubject().getPrincipal();
            //创建jwt token
            String token = Tools.createToken(12, userDTO.getUserId(), userDTO.getUserName(), userDTO.getUserPwd());
            userDTO.setUserToken(token);
            Cache cache = cacheManager.getCache(DEFAULT_TOKEN_CACHENAME);
            cache.put(userDTO.getUserName(), userDTO);
            sysUserService.updateUserLoginInfo(userDTO);
            return Result.ok(StatusCode.SUCCESS, userDTO);
        } catch (Exception e) {
            e.printStackTrace();
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
    @RequestMapping(value = "logout", method = {RequestMethod.GET})
    public Result logout() {
        return Result.ok();
    }

}

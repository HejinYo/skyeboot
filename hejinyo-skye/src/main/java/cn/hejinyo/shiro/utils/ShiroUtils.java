package cn.hejinyo.shiro.utils;

import cn.hejinyo.system.model.dto.CurrentUserDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroUtils {
    private static final Logger logger = LoggerFactory.getLogger(ShiroUtils.class);

    /**
     * 生成用户密码
     *
     * @return
     */
    public static String userPassword(String userPwd, String salt) {
        String algorithmName = "md5";
        int hashIterations = 2;
        SimpleHash hash = new SimpleHash(algorithmName, userPwd, salt, hashIterations);
        return hash.toHex();
    }

    /**
     * 获取用户Subject
     *
     * @return
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取用户对象
     *
     * @return
     */
    public static CurrentUserDTO getCurrentUser() {
        return (CurrentUserDTO) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获得用户id
     *
     * @return
     */
    public static int getUserId() {
        return getCurrentUser().getUserId();
    }

    /**
     * 判断用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    /**
     * 用户注销
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

}

package cn.hejinyo.shiro.realm;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 账户登录多次失败锁定
 *
 * @author HejinYo
 * @version 1.0
 * @email hejinyo@gmail.com
 * @since 1.0
 */
public class CredentialsMatcher extends HashedCredentialsMatcher {
    private static final Logger logger = LoggerFactory.getLogger(CredentialsMatcher.class);
    private static final String DEFAULT_LOGINRECOD_CACHE = "loginRecordCache";

    private Cache<String, AtomicInteger> loginRecordCache;

    public CredentialsMatcher(CacheManager cacheManager) {
        //获得缓存配置
        loginRecordCache = cacheManager.getCache(DEFAULT_LOGINRECOD_CACHE);
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        AtomicInteger retryCount = loginRecordCache.get(username);
        if (retryCount == null) {//如果缓存中没有，就为用户创建一个
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(username, retryCount);
        }
        if (retryCount.incrementAndGet() > 5) {//每次执行登录增加一次，大于5次，抛出异常

            logger.debug("登录名：[" + username + "],登录失败次数：" + retryCount);
            throw new ExcessiveAttemptsException();
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {//认证成功，清除登陆执行次数
            loginRecordCache.remove(username);
        }
        return matches;
    }
}

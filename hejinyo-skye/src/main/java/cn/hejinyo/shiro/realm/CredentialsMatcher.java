package cn.hejinyo.shiro.realm;

import cn.hejinyo.utils.RedisKeys;
import cn.hejinyo.utils.RedisUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        String cacheName = RedisKeys.getLoginRecordCacheKey(username);
        AtomicInteger retryCount = redisUtils.get(cacheName, AtomicInteger.class);
        if (retryCount == null) {//如果缓存中没有，就为用户创建一个
            retryCount = new AtomicInteger(0);
            redisUtils.set(cacheName, retryCount, 1800);
        }
        if (retryCount.incrementAndGet() > 5) {//每次执行登录增加一次，大于5次，抛出异常
            if (6 == retryCount.get()) {
                redisUtils.set(cacheName, retryCount, 1800);
                redisUtils.delete(RedisKeys.getTokenCacheKey(username));
            } else {
                redisUtils.setDefaultExpire(cacheName, retryCount);
            }
            throw new ExcessiveAttemptsException();
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {//认证成功，清除登陆执行次数
            redisUtils.delete(cacheName);
        } else {
            redisUtils.setDefaultExpire(cacheName, retryCount);
        }
        return matches;
    }

}

package cn.hejinyo.utils;

/**
 * Redis所有Keys
 */
public class RedisKeys {

    public static String getShiroCacheKey(String key) {
        return "skye:shiro:" + key;
    }
}

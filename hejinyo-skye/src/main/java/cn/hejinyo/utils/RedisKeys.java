package cn.hejinyo.utils;

/**
 * Redis所有Keys
 */
public class RedisKeys {

    public static String getTokenCacheKey(String key) {
        return "skye-shiro:tokenCache:" + key;
    }

    public static String getAuthCacheKey(String key) {
        return "skye-shiro:authCache:" + key;
    }

    public static String getLoginRecordCacheKey(String key) {
        return "skye-shiro:loginRecordCache:" + key;
    }
}

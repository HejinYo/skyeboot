package cn.hejinyo.utils;

import com.alibaba.fastjson.JSON;

import java.util.Map;

public final class JsonUtils {
    private JsonUtils() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 序列化
     */
    public static String toJSONString(Object entity) {
        return JSON.toJSONString(entity);
    }

    /**
     * 反序列化
     */
    public static <T> T toObject(String json, Class<T> c) {
        return JSON.parseObject(json, c);
    }

    public static Map toMap(String json) {
        return (Map) JSON.parse(json);
    }


}
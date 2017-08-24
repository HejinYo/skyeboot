package cn.hejinyo.utils;

import cn.hejinyo.consts.StatusCode;

import java.util.HashMap;

/**
 * 返回JSON结果 工具类
 */
public class Result extends HashMap<String, Object> {
    private static final int SUCCESS = StatusCode.SUCCESS.getCode();
    private static final int ERROR = StatusCode.FAILURE.getCode();
    private static final int INITIAL = 4;
    private static final String MESSAGE = "message";
    private static final String CODE = "code";
    private static final String RESUTLT = "result";

    public Result() {
        super();
    }

    public Result(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * @return Map： {"code":1}
     */
    public static Result ok() {
        //initialCapacity = (需要存储的元素个数 / 负载因子) + 1。注意负载因子（即loader factor）默认为 0.75，如果暂时无法确定初始值大小，请设置为 16
        Result jsonMap = new Result(INITIAL);
        jsonMap.put(CODE, SUCCESS);
        return jsonMap;
    }

    public static Result ok(String message) {
        Result jsonMap = ok();
        jsonMap.put(MESSAGE, message);
        return jsonMap;
    }

    public static Result ok(Object result) {
        Result jsonMap = ok();
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    public static Result ok(StatusCode statusCode) {
        return error(statusCode.getCode(), statusCode.getMsg());
    }

    public static Result ok(StatusCode statusCode, Object result) {
        Result jsonMap = error(statusCode);
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    public static Result ok(String message, Object result) {
        Result jsonMap = ok(message);
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    /**
     * @return Map： {"code":-1}
     */
    public static Result error() {
        Result jsonMap = new Result(INITIAL);//存放信息的对象
        jsonMap.put(CODE, ERROR);
        return jsonMap;
    }

    public static Result error(int code) {
        Result jsonMap = error();
        jsonMap.put(CODE, code);
        return jsonMap;
    }

    public static Result error(String message) {
        Result jsonMap = error();
        jsonMap.put(MESSAGE, message);
        return jsonMap;
    }

    public static Result error(int code, String message) {
        Result jsonMap = error(code);
        jsonMap.put(MESSAGE, message);
        return jsonMap;
    }

    public static Result error(StatusCode statusCode) {
        return error(statusCode.getCode(), statusCode.getMsg());
    }

    public static Result error(StatusCode statusCode, Object result) {
        Result jsonMap = error(statusCode);
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    public static Result error(Object result) {
        Result jsonMap = error();
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    public static Result error(int code, Object result) {
        Result jsonMap = error(code);
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    public static Result error(String message, Object result) {
        Result jsonMap = error(message);
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    public static Result error(int code, String message, Object result) {
        Result jsonMap = error(code, message);
        jsonMap.put(RESUTLT, result);
        return jsonMap;
    }

    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}

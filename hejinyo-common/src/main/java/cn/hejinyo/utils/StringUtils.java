package cn.hejinyo.utils;

import java.util.Collection;

public final class StringUtils {
    private StringUtils() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String capitalize(String str) {
        return jodd.util.StringUtil.capitalize(str);
    }

    /**
     * 首字母小写
     *
     * @param str
     * @return
     */
    public static String uncapitalize(String str) {
        return jodd.util.StringUtil.uncapitalize(str);
    }

    /**
     * 全部转小写
     *
     * @param str
     * @return
     */
    public static String toLowerCase(String str) {
        return jodd.util.StringUtil.toLowerCase(str);
    }

    /**
     * 连续重复出现的指定字符只保留一个
     *
     * @param str
     * @param c
     * @return
     */
    public static String compressChars(String str, char c) {
        return jodd.util.StringUtil.compressChars(str, c);
    }

    /**
     * 转换字符串字符集。 如果字符集名称相同，则返回相同的字符串。
     *
     * @param str
     * @param srcCharsetName
     * @param newCharsetName
     * @return
     */
    public static String convertCharset(String str, String srcCharsetName, String newCharsetName) {
        return jodd.util.StringUtil.convertCharset(str, srcCharsetName, newCharsetName);
    }

    /**
     * 子字符串在源字符串指定位置出现的次数
     *
     * @param str        源字符
     * @param sub        子字符
     * @param startIndex 查询开始位置
     * @param ignoreCase 忽略大小写
     * @return
     */
    public static int count(String str, String sub, int startIndex, boolean ignoreCase) {
        return ignoreCase ? jodd.util.StringUtil.countIgnoreCase(str.substring(startIndex), sub) : jodd.util.StringUtil.count(str, sub, startIndex);
    }

    /**
     * 删除前缀和后缀
     *
     * @param str
     * @param prefix
     * @param suffix
     * @return
     */
    public static String cut(String str, String prefix, String suffix) {
        String source = str;
        if (jodd.util.StringUtil.isNotEmpty(prefix)) {
            source = jodd.util.StringUtil.cutPrefix(str, prefix);
        }

        if (jodd.util.StringUtil.isNotEmpty(suffix)) {
            source = jodd.util.StringUtil.cutSuffix(source, suffix);
        }

        return source;
    }

    /**
     * 删除前缀
     *
     * @param str
     * @param prefix
     * @return
     */
    public static String cutPrefix(String str, String prefix) {
        String source = str;
        if (jodd.util.StringUtil.isNotEmpty(prefix)) {
            source = jodd.util.StringUtil.cutPrefix(str, prefix);
        }
        return source;
    }

    /**
     * 将字符串从提供的子字符串的第一个索引切割到结尾。
     *
     * @param str
     * @param substring
     * @return
     * @apiNote aabbccdd bbccdd
     */
    public static String cutFrom(String str, String substring) {
        return jodd.util.StringUtil.cutFromIndexOf(str, substring);
    }

    /**
     * 从开始到所提供的子字符串的第一个索引切割字符串。
     *
     * @param str
     * @param substring
     * @return
     * @apiNote aabbccdd aa
     */
    public static String cutTo(String str, String substring) {
        return jodd.util.StringUtil.cutToIndexOf(str, substring);
    }

    /**
     * 将集合内容 拼装成字符串
     *
     * @param collction
     * @param separator
     * @return
     */
    public static String join(Collection<?> collction, String separator) {
        return jodd.util.StringUtil.join(collction, separator);
    }

    /**
     * 是否为Null或为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return jodd.util.StringUtil.isEmpty(str);
    }

    /**
     * 是否不为Null或为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return jodd.util.StringUtil.isNotEmpty(str);
    }

    /**
     * 是否不为Null
     *
     * @param str
     * @return
     */
    public static boolean isNotNull(String str) {
        return null != str;
    }


    /**
     * 是否只含有空格
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return jodd.util.StringUtil.isBlank(str);
    }

    /**
     * 返回两个字符串的相同前缀
     *
     * @param str1
     * @param str2
     * @return
     */
    public static String maxCommonPrefix(String str1, String str2) {
        return jodd.util.StringUtil.maxCommonPrefix(str1, str2);
    }

    /**
     * 如果不存在前缀,则增加前缀
     *
     * @param str
     * @param prefix
     * @return
     */
    public static String prefix(String str, String prefix) {
        return jodd.util.StringUtil.prefix(str, prefix);
    }

    /**
     * 反转字符串
     *
     * @param str
     * @return
     */
    public static String reverse(String str) {
        return jodd.util.StringUtil.reverse(str);
    }

    /**
     * 不存在后缀，则添加后缀
     *
     * @param str
     * @param suffix
     * @return
     */
    public static String suffix(String str, String suffix) {
        return jodd.util.StringUtil.suffix(str, suffix);
    }

    /**
     * 不存在前后缀，则添加前后缀
     *
     * @param str
     * @param prefix
     * @param suffix
     * @return
     */
    public static String surround(String str, String prefix, String suffix) {
        return jodd.util.StringUtil.surround(str, prefix, suffix);
    }

    /**
     * 删除左边空格
     *
     * @param str
     * @return
     */
    public static String trimLeft(String str) {
        return jodd.util.StringUtil.trimLeft(str);
    }

    /**
     * 删除右边空格
     *
     * @param str
     * @return
     */
    public static String trimRight(String str) {
        return jodd.util.StringUtil.trimRight(str);
    }

    /**
     * 转换为下划线
     *
     * @param camelCaseName
     * @return
     */
    public static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    public static boolean isNotBlank(String str) {
        return jodd.util.StringUtil.isNotBlank(str);
    }
}

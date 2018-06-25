/*
 * FileName: StringUtils.java
 * Author:   Arshle
 * Date:     2018年06月12日
 * Description: 字符串操作工具类
 */
package com.jsptpd.netty.utils;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 〈字符串操作工具类〉<br>
 * 〈基础字符串操作〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class StringUtils {

    private static Pattern NUMBER_PATTERN = Pattern.compile("^-?\\d+$");
    /**
     * 是否为空
     * @param value 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String value){
        return value == null || "".equals(value) || value.length() == 0 || "".equals(value.trim());
    }
    /**
     * 是否不为空
     * @param value 字符串
     * @return 是否不为空
     */
    public static boolean isNotEmpty(String value){
        return !isEmpty(value);
    }
    /**
     * 是否是数字
     * @param value 字符串
     * @return 是否是数字
     */
    public static boolean isInteger(String value){
        return isNotEmpty(value) && NUMBER_PATTERN.matcher(value).matches();
    }
    /**
     * 是否是ASCII码
     * @param c 字符
     * @return 是否
     */
    public static boolean isAsciiExtend(char c){
        // ASCII扩展码:128-255(每个字符占2字节)
        int begin = 128;
        int end = 255;
        return c >= begin && c <= end;
    }
    /**
     * 判断字符是否是中文
     * @param c 字符
     * @return 是否
     */
    public static boolean isChinese(char c){
        // 假定英文范围为0-127(ASCII),128-255(ASCII扩展)
        return c > 255;
    }
    /**
     * 字符串长度
     * @param str 字符串
     * @param size 长度
     * @return 长度
     */
    public static int length(String str, int size){
        int totalLength = 0;
        char[] chars = str.toCharArray();
        for (char c : chars){
            if (isAsciiExtend(c)){
                totalLength += 2;
            }else if (isChinese(c)){
                totalLength += size;
            }else{
                totalLength++;
            }
        }
        return totalLength;
    }
    /**
     * 获取全局唯一的id
     * @return uuid
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0,23);
    }
}

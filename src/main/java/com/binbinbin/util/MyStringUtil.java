package com.binbinbin.util;

/**
 * Created by bin on 2017/11/7.
 */
public class MyStringUtil {
    public static String effectiveTimeGetYear(String str){
        String [] strs=str.split("-");
        return strs[1];
    }
    public static String effectiveTimeGetMonth(String str){
        String [] strs=str.split("-");
        return strs[0];
    }
    public static boolean isNotEmpty(String str){
        return null!=str&&!"".equals(str);
    }
    public static Integer valueOf(String value, int def) {
        try {
            if (isNotEmpty(value)) {
                return Integer.parseInt(value);
            }
        } catch (Exception e) {
            return def;
        }
        return def;
    }
}

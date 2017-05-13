package org.smart4j.chapter2.util;

/**
 * 转型操作工具类
 * Created by ZDD on 2017/5/13.
 */
//因为此类为最基本的工具类，所以声明为final
public final class CastUtil {
    /**
     * 转为String型，null即返回""
     */
    public static String castString(Object obj){
        return castString(obj, "");
    }

    /**
     * 转为String型(指定默认值)
     */
    public static String castString(Object obj, String defaultValue){
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转为double型，null或者字符串无法转为浮点型时，即返回0
     */
    public static double castDouble(Object obj){
        return castDouble(obj, 0);
    }

    /**
     * 转为double型（指定默认值）
     */
    public static double castDouble(Object obj, double defaultValue){
        double doubleValue = defaultValue;
        if (obj != null){
            String strValue = String.valueOf(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                //要考虑无法转为浮点型的特殊字符串
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     * 转为long型，null或者无法转换，即返回0
     */
    public static long castLong(Object obj){
        return castLong(obj, 0);
    }

    /**
     * 转为long型，指定默认值
     */
    public static long castLong(Object obj, long defaultValue){
        long longValue = defaultValue;
        if (obj != null){
            String strValue = String.valueOf(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try{
                    longValue = Long.valueOf(strValue);
                }catch(NumberFormatException e){
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    /**
     * 转为int型，null或者无法转换，即返回0
     */
    public static int castInt(Object obj){
        return castInt(obj, 0);
    }

    /**
     * 转为int型，指定默认值
     */
    public static int castInt(Object obj, int defaultValue){
        int intValue = defaultValue;
        if (obj != null){
            String strValue = String.valueOf(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try{
                    intValue = Integer.valueOf(strValue);
                }catch(NumberFormatException e){
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    /**
     * 转为boolean型，null或者无法转换，即返回false
     */
    public static boolean castBoolean(Object obj){
        return castBoolean(obj, false);
    }

    /**
     * 转为boolean型，指定默认值
     */
    public static boolean castBoolean(Object obj, boolean defaultValue){
        boolean booleanValue = defaultValue;
        if (obj != null){
            booleanValue = Boolean.valueOf(castString(obj));
        }
        return booleanValue;
    }
}

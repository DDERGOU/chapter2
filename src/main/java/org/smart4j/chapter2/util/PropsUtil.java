package org.smart4j.chapter2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性(*.properties)文件工具类
 * Created by ZDD on 2017/5/13.
 */
public final class PropsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     */
    public static Properties loadProps(String fileName){
        Properties props = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + "is not found");
            }
            props = new Properties();
            props.load(is);
        }catch(IOException e){
            LOGGER.error("load properties file failure", e);
        }finally{
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close properties file input stream failure", e);
                }
            }
        }
        return props;
    }

    /**
     * 获取字符型属性（默认值为空字符串）
     */
    public static String getString(Properties props, String key){
        return getString(props, key, "");//直接调用可指定默认值的获取字符型属性方法
    }

    /**
     * 获取字符型属性（可指定默认值）
     */
    public static String getString(Properties props, String key, String defaultValue){
        String value = defaultValue;
        if (props.containsKey(key)){
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * 获取整型属性（默认值为0）
     */
    public static int getInt(Properties props, String key){
        return getInt(props, key, 0);
    }

    /**
     * 获取整型属性（可指定默认值）
     */
    public static int getInt(Properties props, String key, int defaultValue){
        int value = defaultValue;
        if (props.containsKey(key)){
            value = Integer.valueOf(props.getProperty(key));
        }
        return value;
    }
}

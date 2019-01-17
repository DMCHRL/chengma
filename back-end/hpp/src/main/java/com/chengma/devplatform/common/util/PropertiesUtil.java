package com.chengma.devplatform.common.util;

import com.chengma.devplatform.config.Constants;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置文件处理工具
 */
public class PropertiesUtil {

    private Properties props = new Properties();

    /**
     * 构造方法
     *
     * @param fileName 配置文件名
     */
    public PropertiesUtil(String fileName) {
        String expandName = ".properties";
        String name = "";
        if (fileName.contains(expandName)) {
            name = fileName;
        } else {
            name = fileName + expandName;
        }
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(name);
            props.load(new InputStreamReader(in, Constants.CHAR_SET));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造方法
     *
     * @param is 输入流（用于获取配置文件）
     */
    public PropertiesUtil(InputStream is) {
        try {
            props.load(new InputStreamReader(is, Constants.CHAR_SET));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取属性
     *
     * @param key 属性名
     * @return 属性内容
     */
    public String getValue(String key) {
        String value = props.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return key;
        }
        return value;
    }

}
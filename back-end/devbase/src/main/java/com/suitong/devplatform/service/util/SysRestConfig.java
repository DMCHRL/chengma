package com.suitong.devplatform.service.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;

public class SysRestConfig {
    private Properties props = null;// config.properties
    private static Logger log = Logger.getLogger(SysRestConfig.class);
    private static volatile SysRestConfig conf;

    private SysRestConfig() {
        props = new Properties();
        loadConfigProps();
    }

    public static SysRestConfig getInstance() {
        if (conf == null) {
            synchronized (SysRestConfig.class) {
                if (conf == null) {
                    conf = new SysRestConfig();
                }
            }
        }
        return conf;
    }

    public void loadConfigProps() {
        InputStream is = null;
        try {
            String url = getClass().getResource("/").toString();
            url = url.substring(5,url.length()) + "config/restconfig.properties";
            url = getRootpath(url);
            is = new BufferedInputStream(new FileInputStream(url));

            InputStreamReader reader = new InputStreamReader(is, "UTF-8");
            props.load(reader);
            Iterator<String> iter = props.stringPropertyNames().iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                props.setProperty(key, props.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("load error! please check the file!", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                    is = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getProperty(String key) {
        String tmp = props.getProperty(key);
        if (StringUtils.isNotEmpty(tmp)) {
            return tmp.trim();
        }
        return tmp;
    }

    public String getProperty(String key, String defaultValue) {
        String tmp = props.getProperty(key, defaultValue);
        if (StringUtils.isNotEmpty(tmp)) {
            return tmp.trim();
        }
        return tmp;
    }

    public int getPropertyInt(String key) {
        String tmp = props.getProperty(key);
        if (StringUtils.isNotEmpty(tmp)) {
            return Integer.parseInt(tmp.trim());
        }
        return 0;

    }

    public int getPropertyInt(String key, int defaultValue) {
        String tmp = props.getProperty(key);
        if (StringUtils.isNotEmpty(tmp)) {
            return Integer.parseInt(tmp.trim());
        }
        return defaultValue;
    }

    public long getPropertyLong(String key, long defaultValue) {
        String tmp = props.getProperty(key);
        if (StringUtils.isNotEmpty(tmp)) {
            return Integer.parseInt(tmp.trim());
        }
        return defaultValue;
    }

    public static String getRootpath(String rootPath) {
        //windows下
        if("\\".equals(File.separator)){
            rootPath = rootPath.replace("/", "\\");
        }
        //linux下
        if("/".equals(File.separator)){
            rootPath = rootPath.replace("\\", "/");
        }
        return rootPath;
    }
}

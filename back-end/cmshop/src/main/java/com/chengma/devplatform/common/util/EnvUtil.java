package com.chengma.devplatform.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 获取yml配置文件的属性
 * Created by Administrator on 2017/12/6.
 */
@Component
public class EnvUtil {

    @Autowired
    private Environment env;
    private static EnvUtil envUtil;

    @PostConstruct
    public void init() {
        envUtil = this;
        envUtil.env = this.env;
    }

    /**
     * 获取yml配置文件的属性
     *
     * @param key 属性名
     * @return 属性内容以字符串形式返回
     */
    public static String getProperty(String key) {
        return envUtil.env.getProperty(key);
    }

    public static String getAdminUserId() {
        return envUtil.env.getProperty("gzCompanyUserId");
    }
}

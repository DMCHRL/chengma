package com.chengma.devplatform.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/29.
 */
@ConfigurationProperties(prefix = "shortMessage") //匹配yml文件中shortMessage下的属性
public class SysRest {
    private String accountSid;
    private String token;
    private String appId;
    private Integer expiryTime;
    private Map<String, String> templates = new HashMap<>(); //接收prop1里面的属性值

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Integer expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Map<String, String> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<String, String> templates) {
        this.templates = templates;
    }
}
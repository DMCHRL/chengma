package com.chengma.devplatform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/29.
 */
@ConfigurationProperties(prefix = "xxpay") //匹配yml文件中shortMessage下的属性
public class XXpayProperties {

    private String appId;
    private String mchId;
    private String reqKey;
    private String repKey;
    private String baseUrl;
    private String baseNotifyUrl;
    private String bindNotifyUrl;
    private String orderNotifyUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getReqKey() {
        return reqKey;
    }

    public void setReqKey(String reqKey) {
        this.reqKey = reqKey;
    }

    public String getRepKey() {
        return repKey;
    }

    public void setRepKey(String repKey) {
        this.repKey = repKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseNotifyUrl() {
        return baseNotifyUrl;
    }

    public void setBaseNotifyUrl(String baseNotifyUrl) {
        this.baseNotifyUrl = baseNotifyUrl;
    }

    public String getBindNotifyUrl() {
        return bindNotifyUrl;
    }

    public void setBindNotifyUrl(String bindNotifyUrl) {
        this.bindNotifyUrl = bindNotifyUrl;
    }

    public String getOrderNotifyUrl() {
        return orderNotifyUrl;
    }

    public void setOrderNotifyUrl(String orderNotifyUrl) {
        this.orderNotifyUrl = orderNotifyUrl;
    }
}
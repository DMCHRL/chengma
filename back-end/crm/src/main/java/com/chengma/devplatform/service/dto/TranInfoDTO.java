package com.chengma.devplatform.service.dto;


import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the TranInfo entity.
 */
public class TranInfoDTO implements Serializable {

    private String id;

    private String appid;

    private String mchId;

    private String openId;

    private String prepayId;

    private String outTradeNo;

    private String transactionId;

    private String body;

    private String totalFee;

    private String paySuccess;

    private String userId;

    private Date createAt;

    private String totalFeeUsd;

    private String obj;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getPaySuccess() {
        return paySuccess;
    }

    public void setPaySuccess(String paySuccess) {
        this.paySuccess = paySuccess;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTotalFeeUsd() {
        return totalFeeUsd;
    }

    public void setTotalFeeUsd(String totalFeeUsd) {
        this.totalFeeUsd = totalFeeUsd;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}

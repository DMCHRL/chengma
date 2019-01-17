package com.chengma.devplatform.domain;
import com.chengma.devplatform.common.entity.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * A TranInfo.
 */
@Entity
@Table(name = "t_tran_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TranInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_appid")
    private String appid;

    @Column(name = "c_mch_id")
    private String mchId;

    @Column(name = "c_open_id")
    private String openId;

    @Column(name = "c_prepay_id")
    private String prepayId;

    @Column(name = "c_out_trade_no")
    private String outTradeNo;

    @Column(name = "c_transaction_id")
    private String transactionId;

    @Column(name = "c_body")
    private String body;

    @Column(name = "c_total_fee")
    private String totalFee;

    @Column(name = "c_total_fee_usd")
    private String totalFeeUsd;

    @Column(name = "c_obj")
    private String obj;

    @Column(name = "c_pay_success")
    private String paySuccess;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    @Column(name = "c_user_id")
    private String userId;

    @Column(name = "d_create_at")
    private Date createDt;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

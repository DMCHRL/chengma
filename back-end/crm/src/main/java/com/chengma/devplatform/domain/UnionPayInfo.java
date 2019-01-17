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
@Table(name = "t_union_pay_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UnionPayInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_merchant_order_no")
    private String merchantOrderNo;

    @Column(name = "c_merchant_user_id")
    private String merchantUserId;

    @Column(name = "c_pay_amount")
    private String payAmount;

    @Column(name = "c_description")
    private String description;

    @Column(name = "c_pay_success")
    private String paySuccess;

    @Column(name = "d_create_at")
    private Date createDt;

    @Column(name = "d_update_at")
    private Date updateAt;

    @Column(name = "c_merchant_id")
    private String merchantId; //商户号

    @Column(name = "c_trans_no")
    private String transNo; //汇付宝订单号

    @Column(name = "c_pay_amount_usd")
    private String payAmountUsd; //支付美金

    @Column(name = "c_obj")
    private String obj; //操作对象

    @Column(name = "c_bank_card_type")
    private String bankCardType; //银行卡类型

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaySuccess() {
        return paySuccess;
    }

    public void setPaySuccess(String paySuccess) {
        this.paySuccess = paySuccess;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getPayAmountUsd() {
        return payAmountUsd;
    }

    public void setPayAmountUsd(String payAmountUsd) {
        this.payAmountUsd = payAmountUsd;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(String bankCardType) {
        this.bankCardType = bankCardType;
    }
}

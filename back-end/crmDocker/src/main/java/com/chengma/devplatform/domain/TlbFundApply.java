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
@Table(name = "t_tlb_fund_apply")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TlbFundApply extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_user_id")
    private String userId;

    @Column(name = "c_account")
    private String account;

    @Column(name = "c_fund_type")
    private String fundType;

    @Column(name = "i_amount")
    private Double amount;

    @Column(name = "c_status")
    private String status;

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "c_approved_id")
    private String approvedId;

    @Column(name = "d_approved_at")
    private Date approvedAt;

    @Column(name = "i_cny_amount")
    private Double cnyAmount;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Column(name = "c_transaction_id")
    private String transactionId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getApprovedId() {
        return approvedId;
    }

    public void setApprovedId(String approvedId) {
        this.approvedId = approvedId;
    }

    public Date getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(Date approvedAt) {
        this.approvedAt = approvedAt;
    }

    public Double getCnyAmount() {
        return cnyAmount;
    }

    public void setCnyAmount(Double cnyAmount) {
        this.cnyAmount = cnyAmount;
    }
}

package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * A SysRole.
 */
@Entity
@Table(name = "t_tlb_account")
public class TlbAccount extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_account")
    private String account;

    @Column(name = "c_account_name")
    private String accountName;

    @Column(name = "c_group")
    private String group;

    @Column(name = "c_ea_group")
    private String eaGroup;

    @Column(name = "c_sink_type")
    private String sinkType;

    @Column(name = "c_email")
    private String email;

    @Column(name = "c_mt4_password")
    private String mt4Password;

    @Column(name = "i_balance")
    private Double balance;

    @Column(name = "i_free_margin")
    private Double freeMargin;

    @Column(name = "i_margin")
    private Double margin;

    @Column(name = "i_profit")
    private Double profit;

    @Column(name = "c_mobile_num")
    private String mobileNum;

    @Column(name = "c_comment")
    private String comment;

    @Column(name = "c_enable_trade")
    private String enableTrade;

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "c_see_password")
    private String seePassword;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSinkType() {
        return sinkType;
    }

    public void setSinkType(String sinkType) {
        this.sinkType = sinkType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMt4Password() {
        return mt4Password;
    }

    public void setMt4Password(String mt4Password) {
        this.mt4Password = mt4Password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getFreeMargin() {
        return freeMargin;
    }

    public void setFreeMargin(Double freeMargin) {
        this.freeMargin = freeMargin;
    }

    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getEnableTrade() {
        return enableTrade;
    }

    public void setEnableTrade(String enableTrade) {
        this.enableTrade = enableTrade;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEaGroup() {
        return eaGroup;
    }

    public void setEaGroup(String eaGroup) {
        this.eaGroup = eaGroup;
    }

    public String getSeePassword() {
        return seePassword;
    }

    public void setSeePassword(String seePassword) {
        this.seePassword = seePassword;
    }
}

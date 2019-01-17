package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * A SysRole.
 */

public class TlbAccountDTO {

    private String id;

    private String account;

    private String accountName;

    private String group;

    private String eaGroup;

    private String eaGroupName;

    private String company;

    private String email;

    private String mt4Password;

    private Double balance;

    private Double freeMargin;

    private Double margin;

    private Double profit;

    private String userId;

    private String enableTrade;

    private String parentName;

    private String  control; //关联账号表的id，用于删除关联

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createAt;

    private String comment;

    private Double successRate;

    private Double successCount;

    private Double totalCount;

    private Double totalLots;

    private String seePassword;

    private String tradeFlag;

    private Double fundIn;

    private Double outTotal; //提现总金额

    private Double firstName; //归属人名称

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
        this.successRate = successRate;
    }

    public Double getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Double successCount) {
        this.successCount = successCount;
    }

    public Double getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Double totalCount) {
        this.totalCount = totalCount;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnableTrade() {
        return enableTrade;
    }

    public void setEnableTrade(String enableTrade) {
        this.enableTrade = enableTrade;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public Double getTotalLots() {
        return totalLots;
    }

    public void setTotalLots(Double totalLots) {
        this.totalLots = totalLots;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getEaGroup() {
        return eaGroup;
    }

    public void setEaGroup(String eaGroup) {
        this.eaGroup = eaGroup;
    }

    public String getEaGroupName() {
        return eaGroupName;
    }

    public void setEaGroupName(String eaGroupName) {
        this.eaGroupName = eaGroupName;
    }

    public String getSeePassword() {
        return seePassword;
    }

    public void setSeePassword(String seePassword) {
        this.seePassword = seePassword;
    }

    public String getTradeFlag() {
        return tradeFlag;
    }

    public void setTradeFlag(String tradeFlag) {
        this.tradeFlag = tradeFlag;
    }

    public Double getFundIn() {
        return fundIn;
    }

    public void setFundIn(Double fundIn) {
        this.fundIn = fundIn;
    }

    public Double getOutTotal() {
        return outTotal;
    }

    public void setOutTotal(Double outTotal) {
        this.outTotal = outTotal;
    }

    public Double getFirstName() {
        return firstName;
    }

    public void setFirstName(Double firstName) {
        this.firstName = firstName;
    }
}
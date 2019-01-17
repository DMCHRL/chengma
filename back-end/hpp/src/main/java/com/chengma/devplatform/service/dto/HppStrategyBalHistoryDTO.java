package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import java.util.Date;

/**
 * A DTO for the SysUserRole entity.
 */
public class HppStrategyBalHistoryDTO {

    private String id;

    private String account;

    private String accountServer;

    private String accountName;    //帐号名称

    private String company;    //所属公司

    private Double balance;    //账户余额(美金)

    private Double freeMargin;    //可用保证金

    private Double margin;    //已用保证金

    private Double equery;    //净值

    private Double profit;    //盈利金额

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date mt4ServerTime;         //mt4时间

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date dbServerTime;         //数据库时间

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createAt;         //创建时间

    private String remark;         //备注

    private String  days;         //增长率的时间节点

    private Double growthRate;   //增长率

    private Double inAmount;   //入金

    private Double outAmount;  //出金

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountServer() {
        return accountServer;
    }

    public void setAccountServer(String accountServer) {
        this.accountServer = accountServer;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public Double getEquery() {
        return equery;
    }

    public void setEquery(Double equery) {
        this.equery = equery;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Date getMt4ServerTime() {
        return mt4ServerTime;
    }

    public void setMt4ServerTime(Date mt4ServerTime) {
        this.mt4ServerTime = mt4ServerTime;
    }

    public Date getDbServerTime() {
        return dbServerTime;
    }

    public void setDbServerTime(Date dbServerTime) {
        this.dbServerTime = dbServerTime;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(Double growthRate) {
        this.growthRate = growthRate;
    }

    public Double getInAmount() {
        return inAmount;
    }

    public void setInAmount(Double inAmount) {
        this.inAmount = inAmount;
    }

    public Double getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(Double outAmount) {
        this.outAmount = outAmount;
    }
}

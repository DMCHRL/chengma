package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import java.util.Date;

/**
 * A SysRole.
 */
public class AssetDTO {

    private String id;

    private String userId;

    private Double balance;   //余额

    private Double total;  //账户总资产

    private Double used;  //当前投资金额

    private Double netProfit;  //累计净收益

    private Double fundProfit;  //当前入伙盈亏

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getUsed() {
        return used;
    }

    public void setUsed(Double used) {
        this.used = used;
    }

    public Double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(Double netProfit) {
        this.netProfit = netProfit;
    }

    public Double getFundProfit() {
        return fundProfit;
    }

    public void setFundProfit(Double fundProfit) {
        this.fundProfit = fundProfit;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}

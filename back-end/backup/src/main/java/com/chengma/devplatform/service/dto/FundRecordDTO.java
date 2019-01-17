package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import java.util.Date;

/**
 * A SysRole.
 */
public class FundRecordDTO {

    private String id;

    private String fundSignalId;

    private String userName;

    private String userId;

    private Double money;      //入伙金额

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    private Double total; //总入金

    private Double num; //入伙人数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFundSignalId() {
        return fundSignalId;
    }

    public void setFundSignalId(String fundSignalId) {
        this.fundSignalId = fundSignalId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }
}

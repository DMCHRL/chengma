package com.chengma.devplatform.service.dto;

import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Created by Administrator on 2018/7/11.
 */
public class TlbCommissionDTO {

    private String id;

    private String userId;

    private Double lotAmount;

    private Double lotPercent;

    private Double total;

    private Double balance;

    private Double withdraw;

    private String login;

    private String firstName;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createAt;

    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

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

    public Double getLotAmount() {
        return lotAmount;
    }

    public void setLotAmount(Double lotAmount) {
        this.lotAmount = lotAmount;
    }

    public Double getLotPercent() {
        return lotPercent;
    }

    public void setLotPercent(Double lotPercent) {
        this.lotPercent = lotPercent;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Double withdraw) {
        this.withdraw = withdraw;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}

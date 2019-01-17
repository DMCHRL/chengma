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
@Table(name = "t_tlb_account_back")
public class TlbAccountBack extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_account")
    private String account;

    @Column(name = "c_account_name")
    private String accountName;

    @Column(name = "i_balance")
    private Double balance;

    @Column(name = "i_free_margin")
    private Double freeMargin;

    @Column(name = "i_margin")
    private Double margin;

    @Column(name = "i_profit")
    private Double profit;

    @Column(name = "i_in")
    private Double in;

    @Column(name = "i_out")
    private Double out;

    @Column(name = "i_bal")
    private Double bal;

    @Column(name = "d_create_at")
    private Date createAt;

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

    public Double getIn() {
        return in;
    }

    public void setIn(Double in) {
        this.in = in;
    }

    public Double getOut() {
        return out;
    }

    public void setOut(Double out) {
        this.out = out;
    }

    public Double getBal() {
        return bal;
    }

    public void setBal(Double bal) {
        this.bal = bal;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}

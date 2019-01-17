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
@Table(name = "t_tlb_commission")
public class TlbCommission extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_user_id")
    private String userId;

    @Column(name = "i_lot_amount")
    private Double lotAmount;

    @Column(name = "i_lot_percent")
    private Double lotPercent;

    @Column(name = "i_total")
    private Double total;

    @Column(name = "i_balance")
    private Double balance;

    @Column(name = "i_withdraw")
    private Double withdraw;

    @Column(name = "d_create_at")
    private Date createAt;

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
}

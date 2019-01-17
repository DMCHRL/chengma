package com.chengma.devplatform.domain;

import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/9.
 */
@Entity
@Table(name="t_hpp_integral")
public class HppIntegral extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="c_mobile_num")
    private String mobileNum;

    @Column(name="i_total")
    private Double total;

    @Column(name="i_balance")
    private Double balance;

    @Column(name="i_used")
    private Double used;

    @Column(name="d_create_at")
    private Date createAt;

    @Column(name = "d_update_at")
    private Date updateAt;

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
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

    public Double getUsed() {
        return used;
    }

    public void setUsed(Double used) {
        this.used = used;
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

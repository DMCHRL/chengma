package com.suitong.devplatform.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A SysRole.
 */
//代理
@Entity
@Table(name = "t_tlb_rate")
public class TlbRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_rate")
    private BigDecimal rate;//提现汇率

    @Column(name = "c_recharge")
    private BigDecimal recharge;//充值汇率

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getRecharge() {
        return recharge;
    }

    public void setRecharge(BigDecimal recharge) {
        this.recharge = recharge;
    }

    @Override
    public String toString() {
        return "TlbRate{" +
                "id=" + id +
                ", rate=" + rate +
                ", recharge=" + recharge +
                '}';
    }
}
package com.suitong.devplatform.service.dto;


import java.math.BigDecimal;

/**
 * A SysRole.
 */
public class TlbRateDTO {

    private Long id;

    private BigDecimal rate;//提现汇率

    private BigDecimal recharge;//充值汇率

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
        return "TlbRateDTO{" +
                "id=" + id +
                ", rate=" + rate +
                ", recharge=" + recharge +
                '}';
    }
}

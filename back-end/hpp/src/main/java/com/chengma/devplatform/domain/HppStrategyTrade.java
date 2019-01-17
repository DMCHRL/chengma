package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 策略持仓记录
 */
@Entity
@Table(name = "t_hpp_strategy_trade")
public class HppStrategyTrade extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "i_order_no")
    private Long orderNo;

    @Column(name = "c_order_type")
    private String orderType;

    @Column(name = "c_ordered")
    private String ordered;

    @Column(name = "c_symbol")
    private String symbol;

    @Column(name = "i_lots")
    private Double lots;

    @Column(name = "i_sl")
    private Double sl;

    @Column(name = "i_tp")
    private Double tp;

    @Column(name = "i_open_price")
    private Double openPrice;

    @Column(name = "d_open_time")
    private Date openTime;

    @Column(name = "i_close_price")
    private Double closePrice;

    @Column(name = "d_close_time")
    private Date closeTime;

    @Column(name = "c_closed")
    private String closed;

    @Column(name = "c_gain")
    private String gain;

    @Column(name = "c_gain_amount")
    private Double gainAmount;

    @Column(name = "i_change_amount")
    private Double changeAmount;    //手续费

    @Column(name = "i_profit_amount")
    private Double profitAmount;    //获利(美元)

    @Column(name = "i_stock_amount")
    private Double stockAmount;    //库存费

    @Column(name = "c_comment")
    private String comment;    //注释

    @Column(name = "c_calc_sum")
    private String calcSum;

    @Column(name = "c_account")
    private String account;

    @Column(name = "d_create_at")
    private Date createAt;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrdered() {
        return ordered;
    }

    public void setOrdered(String ordered) {
        this.ordered = ordered;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getLots() {
        return lots;
    }

    public void setLots(Double lots) {
        this.lots = lots;
    }

    public Double getSl() {
        return sl;
    }

    public void setSl(Double sl) {
        this.sl = sl;
    }

    public Double getTp() {
        return tp;
    }

    public void setTp(Double tp) {
        this.tp = tp;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public String getGain() {
        return gain;
    }

    public void setGain(String gain) {
        this.gain = gain;
    }

    public Double getGainAmount() {
        return gainAmount;
    }

    public void setGainAmount(Double gainAmount) {
        this.gainAmount = gainAmount;
    }

    public String getCalcSum() {
        return calcSum;
    }

    public void setCalcSum(String calcSum) {
        this.calcSum = calcSum;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public Double getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(Double profitAmount) {
        this.profitAmount = profitAmount;
    }

    public Double getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(Double stockAmount) {
        this.stockAmount = stockAmount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

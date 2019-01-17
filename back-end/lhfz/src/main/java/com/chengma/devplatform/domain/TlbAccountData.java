package com.chengma.devplatform.domain;

import com.chengma.devplatform.common.entity.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 策略数据
 */
@Entity
@Table(name = "t_tlb_account_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TlbAccountData extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "c_account")
    private String account;

    @Column(name = "i_margin")
    private Double margin;    //初始入金

    @Column(name = "i_month_profit")
    private Double monthProfit;    //当月收益

    @Column(name = "i_total_profit")
    private Double totalProfit;    //累计收益

    @Column(name = "i_fund_back")
    private Double fundBack;    //资金回撤

    @Column(name = "i_lots")
    private Double lots;    //手数

    @Column(name = "i_balance")
    private Double balance;    //余额

    @Column(name = "i_net_worth")
    private Double netWorth;    //净值

    @Column(name = "i_fund_in")
    private Double fundIn;    //入金总额

    @Column(name = "i_fund_out")
    private Double fundOut;    //入金总额

    @Column(name = "i_top_net_worth")
    private Double topNetWorth;    //最高净值

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }

    public Double getMonthProfit() {
        return monthProfit;
    }

    public void setMonthProfit(Double monthProfit) {
        this.monthProfit = monthProfit;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Double getFundBack() {
        return fundBack;
    }

    public void setFundBack(Double fundBack) {
        this.fundBack = fundBack;
    }

    public Double getLots() {
        return lots;
    }

    public void setLots(Double lots) {
        this.lots = lots;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(Double netWorth) {
        this.netWorth = netWorth;
    }

    public Double getFundIn() {
        return fundIn;
    }

    public void setFundIn(Double fundIn) {
        this.fundIn = fundIn;
    }

    public Double getFundOut() {
        return fundOut;
    }

    public void setFundOut(Double fundOut) {
        this.fundOut = fundOut;
    }

    public Double getTopNetWorth() {
        return topNetWorth;
    }

    public void setTopNetWorth(Double topNetWorth) {
        this.topNetWorth = topNetWorth;
    }
}

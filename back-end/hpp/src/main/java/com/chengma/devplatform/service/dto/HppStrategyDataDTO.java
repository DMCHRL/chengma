package com.chengma.devplatform.service.dto;

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
public class HppStrategyDataDTO {

    private String id;

    private String account;

    private Double margin;    //初始入金

    private Double monthProfit;    //当月收益

    private Double totalProfit;    //累计收益

    private Double fundBack;    //资金回撤

    private Double lots;    //手数

    private Double balance;    //余额

    private Double netWorth;    //净值

    private Double fundIn;    //入金总额

    private Double fundOut;    //入金总额

    private Double topNetWorth;    //最高净值

    private String strategyText;  //策略详情

    private String strategyName;  //策略名称

    private String strategyId;    //策略Id

    private String tradeType;    //交易方式

    private String strategyAccount; //策略账号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getStrategyText() {
        return strategyText;
    }

    public void setStrategyText(String strategyText) {
        this.strategyText = strategyText;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getStrategyAccount() {
        return strategyAccount;
    }

    public void setStrategyAccount(String strategyAccount) {
        this.strategyAccount = strategyAccount;
    }
}

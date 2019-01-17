package com.chengma.devplatform.service.dto;

import java.util.Date;

/**
 * Created by Administrator on 2018/10/9.
 */
public class TradeDataDTO {

    private String account;

    private String symbol;

    private String date;

    private Double gainMoney;

    private Double lots;

    private Double successRate;

    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
        this.successRate = successRate;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getGainMoney() {
        return gainMoney;
    }

    public void setGainMoney(Double gainMoney) {
        this.gainMoney = gainMoney;
    }
}

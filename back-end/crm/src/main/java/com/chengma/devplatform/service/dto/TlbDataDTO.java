package com.chengma.devplatform.service.dto;

/**
 * Created by Administrator on 2018/8/10.
 */
public class TlbDataDTO {

    private Double totalTradeCount; //总交易量

    private Double totalLots;        //总手数

    private Double totalGain;        //总盈利

    private Double totalCommission;  //总佣金

    private Double totalAccount;     //真实用户量

    private Double totalSuccessCount; //总成功笔数

    private Double totalSuccessRate;  //总胜率

    private Double avgTradeCount;     //平均交易量

    private Double avgLots;            //平均手数

    private Double avgGain;            //平均盈利

    private Double totalIn;            //总入金

    private Double totalOut;          //总出金

    public Double getTotalTradeCount() {
        return totalTradeCount;
    }

    public void setTotalTradeCount(Double totalTradeCount) {
        this.totalTradeCount = totalTradeCount;
    }

    public Double getTotalLots() {
        return totalLots;
    }

    public void setTotalLots(Double totalLots) {
        this.totalLots = totalLots;
    }

    public Double getTotalGain() {
        return totalGain;
    }

    public void setTotalGain(Double totalGain) {
        this.totalGain = totalGain;
    }

    public Double getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(Double totalCommission) {
        this.totalCommission = totalCommission;
    }

    public Double getTotalAccount() {
        return totalAccount;
    }

    public void setTotalAccount(Double totalAccount) {
        this.totalAccount = totalAccount;
    }

    public Double getTotalSuccessCount() {
        return totalSuccessCount;
    }

    public void setTotalSuccessCount(Double totalSuccessCount) {
        this.totalSuccessCount = totalSuccessCount;
    }

    public Double getTotalSuccessRate() {
        return totalSuccessRate;
    }

    public void setTotalSuccessRate(Double totalSuccessRate) {
        this.totalSuccessRate = totalSuccessRate;
    }

    public Double getAvgTradeCount() {
        return avgTradeCount;
    }

    public void setAvgTradeCount(Double avgTradeCount) {
        this.avgTradeCount = avgTradeCount;
    }

    public Double getAvgLots() {
        return avgLots;
    }

    public void setAvgLots(Double avgLots) {
        this.avgLots = avgLots;
    }

    public Double getAvgGain() {
        return avgGain;
    }

    public void setAvgGain(Double avgGain) {
        this.avgGain = avgGain;
    }

    public Double getTotalIn() {
        return totalIn;
    }

    public void setTotalIn(Double totalIn) {
        this.totalIn = totalIn;
    }

    public Double getTotalOut() {
        return totalOut;
    }

    public void setTotalOut(Double totalOut) {
        this.totalOut = totalOut;
    }
}

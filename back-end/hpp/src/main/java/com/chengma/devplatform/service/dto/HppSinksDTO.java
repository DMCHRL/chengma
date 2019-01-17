package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import java.util.Date;

/**
 * A DTO for the SysUserRole entity.
 */
public class HppSinksDTO {

    private String id;

    private String sinksName;   //汇商名称

    private String sinksName2;   //汇商次名

    private String explain;      //列表摘要

    private String listImg;     //列表显示图标

    private String background;     //背景图

    private String openTime;     //成立时间

    private String country;     //所属国家

    private Double lots;     //最小手数

    private String lever;     //最大杠杆

    private String pointType;     //点数类型

    private String mainPoint;     //主要点差

    private String tradeType;     //交易品种

    private String remark;     //平台介绍

    private String platformType;     //平台类型

    private String accountType;     //开设账户类型

    private String peel;     //剥头皮 Y or N (支持 or 不支持)

    private String hedging;     //对冲 Y or N (支持 or 不支持)

    private String maxTrade;     //最大交易量

    private String fundOut;     //出金方式

    private String fundIn;     //入金方式

    private String fundOutCost;     //出金手续费

    private String fundInCost;     //入金手续费

    private String rollovers;     //隔夜利息

    private String explosionRatio;     //爆仓比例

    private String rmb;     //是否支持人民币入金 (Y or N) (支持 or 不支持)

    private String evaluate;     //评价

    private String evaluateScore;     //评价分数

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createTime;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSinksName() {
        return sinksName;
    }

    public void setSinksName(String sinksName) {
        this.sinksName = sinksName;
    }

    public String getSinksName2() {
        return sinksName2;
    }

    public void setSinksName2(String sinksName2) {
        this.sinksName2 = sinksName2;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getListImg() {
        return listImg;
    }

    public void setListImg(String listImg) {
        this.listImg = listImg;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLots() {
        return lots;
    }

    public void setLots(Double lots) {
        this.lots = lots;
    }

    public String getLever() {
        return lever;
    }

    public void setLever(String lever) {
        this.lever = lever;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getMainPoint() {
        return mainPoint;
    }

    public void setMainPoint(String mainPoint) {
        this.mainPoint = mainPoint;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPeel() {
        return peel;
    }

    public void setPeel(String peel) {
        this.peel = peel;
    }

    public String getHedging() {
        return hedging;
    }

    public void setHedging(String hedging) {
        this.hedging = hedging;
    }

    public String getMaxTrade() {
        return maxTrade;
    }

    public void setMaxTrade(String maxTrade) {
        this.maxTrade = maxTrade;
    }

    public String getFundOut() {
        return fundOut;
    }

    public void setFundOut(String fundOut) {
        this.fundOut = fundOut;
    }

    public String getFundIn() {
        return fundIn;
    }

    public void setFundIn(String fundIn) {
        this.fundIn = fundIn;
    }

    public String getFundOutCost() {
        return fundOutCost;
    }

    public void setFundOutCost(String fundOutCost) {
        this.fundOutCost = fundOutCost;
    }

    public String getFundInCost() {
        return fundInCost;
    }

    public void setFundInCost(String fundInCost) {
        this.fundInCost = fundInCost;
    }

    public String getRollovers() {
        return rollovers;
    }

    public void setRollovers(String rollovers) {
        this.rollovers = rollovers;
    }

    public String getExplosionRatio() {
        return explosionRatio;
    }

    public void setExplosionRatio(String explosionRatio) {
        this.explosionRatio = explosionRatio;
    }

    public String getRmb() {
        return rmb;
    }

    public void setRmb(String rmb) {
        this.rmb = rmb;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getEvaluateScore() {
        return evaluateScore;
    }

    public void setEvaluateScore(String evaluateScore) {
        this.evaluateScore = evaluateScore;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

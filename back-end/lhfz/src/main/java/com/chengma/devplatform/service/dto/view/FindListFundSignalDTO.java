package com.chengma.devplatform.service.dto.view;


import com.chengma.devplatform.CustomDateSerializer;
import com.chengma.devplatform.service.dto.AllotRuleDTO;
import com.chengma.devplatform.service.dto.ExpectRiskProfitDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;

/**
 * A SysRole.
 */
public class FindListFundSignalDTO {

    private String id;

    private String name;

    private Double yearProfit1;  //预期年收益1

    private Double yearProfit2;  //预期年收益2

    private Double risk;  //合伙人最大风险

    private Integer cycleTime; //操作周期

    private Integer remainingTime; //剩余时间

    private Double progress; //募集进度

    private Double canJoin; //还可入伙金额

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getYearProfit1() {
        return yearProfit1;
    }

    public void setYearProfit1(Double yearProfit1) {
        this.yearProfit1 = yearProfit1;
    }

    public Double getYearProfit2() {
        return yearProfit2;
    }

    public void setYearProfit2(Double yearProfit2) {
        this.yearProfit2 = yearProfit2;
    }

    public Double getRisk() {
        return risk;
    }

    public void setRisk(Double risk) {
        this.risk = risk;
    }

    public Integer getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(Integer cycleTime) {
        this.cycleTime = cycleTime;
    }

    public Integer getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Integer remainingTime) {
        this.remainingTime = remainingTime;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Double getCanJoin() {
        return canJoin;
    }

    public void setCanJoin(Double canJoin) {
        this.canJoin = canJoin;
    }
}

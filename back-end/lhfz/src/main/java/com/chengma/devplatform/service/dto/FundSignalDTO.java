package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * A SysRole.
 */
public class FundSignalDTO {

    private String id;

    private String name;

    private Double yearProfit1;  //预期年收益1

    private Double yearProfit2;  //预期年收益2

    private Double risk;  //合伙人最大风险

    private Double targetFund;  //目标金额

    private Double minFund;  //入伙人最低金额

    private Integer minLevel;  //入伙人最低段位

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date startAt;    //募集开始时间

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date operationAt;    //操作时间

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date endAt;    //计划操作结束时间

    private String successFlag; //募集成功提前进入操作期

    private String partnerFlag; //合伙人的收益达预期可提前清盘

    private String account; //交易账号

    private String originatorId; //发起人id

    private String managerId; //资金管理人id

    private String remark; //产品描述

    private String platform; //交易平台

    private String strategy; //投资策略

    private String direction; //买卖方向

    private String range; //投资范围

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    private String status;        //募集中(APPLYING)，操作中(DOING)，已清盘(FINISH)

    List<AllotRuleDTO> allotRuleDTOList; //分配规则

    List<ExpectRiskProfitDTO> expectRiskProfitDTOList;  //预期风险及收益

    private Integer cycleTime; //操作周期

    private Integer remainingTime; //剩余时间

    private Double progress; //募集进度

    private Integer num; //入伙数

    private Double canJoin; //还可入伙金额

    private String originatorName; //发起人

    private String managerName; //资金管理人

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

    public Double getTargetFund() {
        return targetFund;
    }

    public void setTargetFund(Double targetFund) {
        this.targetFund = targetFund;
    }

    public Double getMinFund() {
        return minFund;
    }

    public void setMinFund(Double minFund) {
        this.minFund = minFund;
    }

    public Integer getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Integer minLevel) {
        this.minLevel = minLevel;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getOperationAt() {
        return operationAt;
    }

    public void setOperationAt(Date operationAt) {
        this.operationAt = operationAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public String getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(String successFlag) {
        this.successFlag = successFlag;
    }

    public String getPartnerFlag() {
        return partnerFlag;
    }

    public void setPartnerFlag(String partnerFlag) {
        this.partnerFlag = partnerFlag;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOriginatorId() {
        return originatorId;
    }

    public void setOriginatorId(String originatorId) {
        this.originatorId = originatorId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AllotRuleDTO> getAllotRuleDTOList() {
        return allotRuleDTOList;
    }

    public void setAllotRuleDTOList(List<AllotRuleDTO> allotRuleDTOList) {
        this.allotRuleDTOList = allotRuleDTOList;
    }

    public List<ExpectRiskProfitDTO> getExpectRiskProfitDTOList() {
        return expectRiskProfitDTOList;
    }

    public void setExpectRiskProfitDTOList(List<ExpectRiskProfitDTO> expectRiskProfitDTOList) {
        this.expectRiskProfitDTOList = expectRiskProfitDTOList;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getCanJoin() {
        return canJoin;
    }

    public void setCanJoin(Double canJoin) {
        this.canJoin = canJoin;
    }

    public String getOriginatorName() {
        return originatorName;
    }

    public void setOriginatorName(String originatorName) {
        this.originatorName = originatorName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}

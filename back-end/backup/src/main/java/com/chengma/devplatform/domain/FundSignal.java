package com.chengma.devplatform.domain;

import com.chengma.devplatform.common.entity.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * A MobileValidate.
 */
@Entity
@Table(name = "t_fund_signal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FundSignal extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_name")
    private String name;

    @Column(name = "i_year_profit1")
    private Double yearProfit1;  //预期年收益1

    @Column(name = "i_year_profit2")
    private Double yearProfit2;  //预期年收益2

    @Column(name = "i_risk")
    private Double risk;  //合伙人最大风险

    @Column(name = "i_target_fund")
    private Double targetFund;  //目标金额

    @Column(name = "i_min_fund")
    private Double minFund;  //入伙人最低金额

    @Column(name = "i_min_level")
    private Integer minLevel;  //入伙人最低段位

    @Column(name = "d_start_at")
    private Date startAt;    //募集开始时间

    @Column(name = "d_operation_at")
    private Date operationAt;    //操作时间

    @Column(name = "d_end_at")
    private Date endAt;    //计划操作结束时间

    @Column(name = "c_success_flag")
    private String successFlag; //募集成功提前进入操作期

    @Column(name = "c_partner_flag")
    private String partnerFlag; //合伙人的收益达预期可提前清盘

    @Column(name = "c_account")
    private String account; //交易账号

    @Column(name = "c_originator_id")
    private String originatorId; //发起人id

    @Column(name = "c_manager_id")
    private String managerId; //资金管理人id

    @Column(name = "c_remark")
    private String remark; //产品描述

    @Column(name = "c_platform")
    private String platform; //交易平台

    @Column(name = "c_strategy")
    private String strategy; //投资策略

    @Column(name = "c_direction")
    private String direction; //买卖方向

    @Column(name = "c_range")
    private String range; //投资范围

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "d_update_at")
    private Date updateAt;

    @Column(name = "c_status")
    private String status;        //募集中(APPLYING)，操作中 OPERATION，已清盘(FINISH)

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

}

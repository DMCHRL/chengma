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
 * 分配规则
 */
@Entity
@Table(name = "t_fund_signal_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FundSignalData extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "i_pre_fund")
    private Double preFund;   //初始资金

    @Column(name = "i_profit")
    private Double profit;   //总资金

    @Column(name = "c_fund_signal_id")
    private String fundSignalId;  //信号Id

    @Column(name = "c_originator_id")
    private String originatorId;  //发起人

    @Column(name = "c_manager_id")
    private String managerId;      //资金管理人

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "d_update_at")
    private Date updateAt;

    @Column(name = "c_status")
    private String status;        //操作中(OPERATION)，已清盘(FINISH)

    public Double getPreFund() {
        return preFund;
    }

    public void setPreFund(Double preFund) {
        this.preFund = preFund;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public String getFundSignalId() {
        return fundSignalId;
    }

    public void setFundSignalId(String fundSignalId) {
        this.fundSignalId = fundSignalId;
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

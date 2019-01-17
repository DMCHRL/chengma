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
 * 策略
 */
@Entity
@Table(name = "t_hpp_strategy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HppStrategy extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_strategy_name")
    private String strategyName;

    @Column(name = "c_strategy_name2")
    private String strategyName2;

    @Column(name = "i_income_rate")
    private Double incomeRate;

    @Column(name = "c_strategy_text")
    private String strategyText;

    @Column(name = "c_account")
    private String account;

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "d_update_at")
    private Date updateAt;

    @Column(name = "c_password")
    private String password;

    @Column(name = "c_user_id")
    private String userId;

    @Column(name = "c_status")
    private String status;

    @Column(name = "d_approve_at")
    private Date approveAt;

    @Column(name = "c_platform")
    private String platform;      //交易平台

    @Column(name = "c_server")
    private String server;      //服务器

    @Column(name = "c_approve_id")
    private String approveId;

    @Column(name = "c_line_flag")
    private String lineFlag;   //控制上下线

    @Column(name = "c_mobile")
    private String mobile;   //入驻者电话号码

    @Column(name = "c_activity_flag")
    private String activityFlag;   //是否允许跟单

    @Column(name = "c_activity_title")
    private String activityTitle;

    @Column(name = "c_activity_text")
    private String activityText;

    @Column(name = "c_trade_type")
    private String tradeType;     //交易方式

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getStrategyName2() {
        return strategyName2;
    }

    public void setStrategyName2(String strategyName2) {
        this.strategyName2 = strategyName2;
    }

    public Double getIncomeRate() {
        return incomeRate;
    }

    public void setIncomeRate(Double incomeRate) {
        this.incomeRate = incomeRate;
    }

    public String getStrategyText() {
        return strategyText;
    }

    public void setStrategyText(String strategyText) {
        this.strategyText = strategyText;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Date getApproveAt() {
        return approveAt;
    }

    public void setApproveAt(Date approveAt) {
        this.approveAt = approveAt;
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getLineFlag() {
        return lineFlag;
    }

    public void setLineFlag(String lineFlag) {
        this.lineFlag = lineFlag;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getActivityFlag() {
        return activityFlag;
    }

    public void setActivityFlag(String activityFlag) {
        this.activityFlag = activityFlag;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityText() {
        return activityText;
    }

    public void setActivityText(String activityText) {
        this.activityText = activityText;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}

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
 * 策略跟单
 */
@Entity
@Table(name = "t_hpp_strategy_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HppStrategyOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_strategy_id")
    private String strategyId;    //策略id

    @Column(name = "c_mail")
    private String mail;       //交易账号

    @Column(name = "c_risk")
    private String risk;          //KEEP(保守) STEADY(稳健) RADICAL(激进)

    @Column(name = "d_create_at")
    private Date createAt;       //跟单时间

    @Column(name = "c_type")
    private String type;            //in(跟单) out(解除)

    @Column(name = "c_status")
    private String status;          //APPLYING PASSED REJECT

    @Column(name = "c_account")
    private String account;         //跟单账号

    @Column(name = "c_password")
    private String password;        //跟单账号密码

    @Column(name = "c_approve_id")
    private String approveId; //处理人id;

    @Column(name = "d_approve_at")
    private Date approveAt;       //跟单时间

    @Column(name = "c_state")
    private String state;        //记录信息是否有效

    @Column(name = "c_mt4_status")
    private String mt4Status;        //mt4状态

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getApproveAt() {
        return approveAt;
    }

    public void setApproveAt(Date approveAt) {
        this.approveAt = approveAt;
    }

    public String getMt4Status() {
        return mt4Status;
    }

    public void setMt4Status(String mt4Status) {
        this.mt4Status = mt4Status;
    }
}

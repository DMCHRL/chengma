package com.chengma.devplatform.domain;

import com.chengma.devplatform.common.entity.BaseEntity;
import com.sun.org.apache.bcel.internal.generic.DADD;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 策略数据
 */
@Entity
@Table(name = "t_hpp_strategy_bal_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HppStrategyBalHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "c_account")
    private String account;

    @Column(name = "c_account_server")
    private String accountServer;

    @Column(name = "c_account_name")
    private String accountName;    //帐号名称

    @Column(name = "c_company")
    private String company;    //所属公司

    @Column(name = "i_balance")
    private Double balance;    //账户余额(美金)

    @Column(name = "i_free_margin")
    private Double freeMargin;    //可用保证金

    @Column(name = "i_margin")
    private Double margin;    //已用保证金

    @Column(name = "i_equery")
    private Double equery;    //净值

    @Column(name = "i_profit")
    private Double profit;    //盈利金额

    @Column(name = "d_mt4_server_time")
    private Date mt4ServerTime;         //mt4时间

    @Column(name = "d_db_server_time")
    private Date dbServerTime;         //数据库时间

    @Column(name = "d_create_at")
    private Date createAt;         //创建时间

    @Column(name = "c_remark")
    private String remark;         //备注

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountServer() {
        return accountServer;
    }

    public void setAccountServer(String accountServer) {
        this.accountServer = accountServer;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getFreeMargin() {
        return freeMargin;
    }

    public void setFreeMargin(Double freeMargin) {
        this.freeMargin = freeMargin;
    }

    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }

    public Double getEquery() {
        return equery;
    }

    public void setEquery(Double equery) {
        this.equery = equery;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Date getMt4ServerTime() {
        return mt4ServerTime;
    }

    public void setMt4ServerTime(Date mt4ServerTime) {
        this.mt4ServerTime = mt4ServerTime;
    }

    public Date getDbServerTime() {
        return dbServerTime;
    }

    public void setDbServerTime(Date dbServerTime) {
        this.dbServerTime = dbServerTime;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

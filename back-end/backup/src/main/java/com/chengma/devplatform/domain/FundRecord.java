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
@Table(name = "t_fund_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FundRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_fund_signal_id")
    private String fundSignalId;

    @Column(name = "c_user_name")
    private String userName;

    @Column(name = "c_user_id")
    private String userId;

    @Column(name = "i_money")
    private Double money;      //入伙金额

    @Column(name = "d_create_at")
    private Date createAt;

    public String getFundSignalId() {
        return fundSignalId;
    }

    public void setFundSignalId(String fundSignalId) {
        this.fundSignalId = fundSignalId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}

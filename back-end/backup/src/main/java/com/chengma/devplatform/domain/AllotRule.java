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
@Table(name = "t_allot_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllotRule extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_fund_signal_id")
    private String fundSignalId;

    @Column(name = "c_role")
    private String role;   //角色名称

    @Column(name = "c_role_flag")
    private String roleFlag;  //标记

    @Column(name = "c_remark")
    private String remark;  //收益分配说明

    @Column(name = "i_rate")
    private Double rate;      //分配比

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "d_update_at")
    private Date updateAt;

    public String getFundSignalId() {
        return fundSignalId;
    }

    public void setFundSignalId(String fundSignalId) {
        this.fundSignalId = fundSignalId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
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

    public String getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(String roleFlag) {
        this.roleFlag = roleFlag;
    }
}

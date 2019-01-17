package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * A 收款方.
 */
@Entity
@Table(name = "t_receivables")
public class Receivables extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_bank")
    private String bank;

    @Column(name = "c_name")
    private String name;

    @Column(name = "c_bank_num")
    private String bankNum;

    @Column(name = "c_remark")
    private String remark;

    @Column(name = "d_update_time")
    private Date updateTime;

    @Column(name = "d_create_time")
    private Date createTime;

    @Column(name = "c_flag")
    private String flag;

    @Column(name = "c_company")
    private String company;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}

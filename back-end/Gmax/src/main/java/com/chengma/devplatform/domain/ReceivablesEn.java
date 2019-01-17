package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * A SysRole.
 */
@Entity
@Table(name = "t_receivables_en")
public class ReceivablesEn extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_user_name")
    private String userName;

    @Column(name = "c_user_name_en")
    private String userNameEn;

    @Column(name = "c_account_type")
    private String accountType;

    @Column(name = "c_account_type_en")
    private String accountTypeEn;

    @Column(name = "c_account")
    private String account;

    @Column(name = "c_bank_name")
    private String bankName;

    @Column(name = "c_bank_name_en")
    private String bankNameEn;

    @Column(name = "c_address")
    private String address;

    @Column(name = "c_address_en")
    private String addressEn;

    @Column(name = "c_bank_code")
    private String bankCode;

    @Column(name = "c_swift_code")
    private String swiftCode;

    @Column(name = "d_update_time")
    private Date updateTime;

    @Column(name = "d_create_time")
    private Date createTime;

    @Column(name = "c_flag")
    private String flag;

    @Column(name = "c_company")
    private String company;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNameEn() {
        return userNameEn;
    }

    public void setUserNameEn(String userNameEn) {
        this.userNameEn = userNameEn;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountTypeEn() {
        return accountTypeEn;
    }

    public void setAccountTypeEn(String accountTypeEn) {
        this.accountTypeEn = accountTypeEn;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNameEn() {
        return bankNameEn;
    }

    public void setBankNameEn(String bankNameEn) {
        this.bankNameEn = bankNameEn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
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

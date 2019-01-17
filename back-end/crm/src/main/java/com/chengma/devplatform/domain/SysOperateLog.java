package com.chengma.devplatform.domain;

import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/24.
 */
@Entity
@Table(name = "t_sys_operate_log")
public class SysOperateLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "C_ACCOUNT")
    private String account;

    @Column(name = "C_USER_NAME")
    private String userName;

    @Column(name = "D_CREATE_TIME")
    private Date createTime;

    @Column(name = "C_ADDRESS")
    private String address;

    @Column(name = "D_LOG_TIME")
    private Date logTime;

    @Column(name = "C_OPERATE_CONTENT")
    private String operateContent;

    @Column(name = "C_TYPE")
    private String type;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.suitong.devplatform.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2017/8/24.
 */
@Entity
@Table(name = "t_sys_operate_log")
public class SysOperateLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="c_id")
    private Long id;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysOperateLog sysOperateLog = (SysOperateLog) o;
        if (sysOperateLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysOperateLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysOperateLog{" +
                "id=" + getId() +
                ", account='" + getAccount() + "'" +
                ", createTime='" + getCreateTime() + "'" +
                ", address='" + getAddress() + "'" +
                ", logTime='" + getLogTime() + "'" +
                ", operateContent='" + getOperateContent() + "'" +
                "}";
    }

}

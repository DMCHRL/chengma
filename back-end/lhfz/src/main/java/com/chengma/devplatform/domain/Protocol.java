package com.chengma.devplatform.domain;

import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 协议书
 */
@Entity
@Table(name = "t_protocol")
public class Protocol extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_context")
    private String context;

    @Column(name = "d_create_time")
    private Date createTime;

    @Column(name = "d_update_time")
    private Date updateTime;

    @Column(name = "c_type")
    private String type;

    @Column(name = "c_type_desc")
    private String typeDesc;

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Column(name = "c_del_flag")
    private String delFlag;

    @Column(name = "c_qq")
    private String qq;

    @Column(name = "c_phone")
    private String phone;

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


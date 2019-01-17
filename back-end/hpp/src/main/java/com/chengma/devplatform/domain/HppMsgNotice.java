package com.chengma.devplatform.domain;

import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/9.
 */
@Entity
@Table(name="t_hpp_msg_notice")
public class HppMsgNotice extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="c_msg_type")
    private String msgType;

    @Column(name="c_msg")
    private String msg;

    @Column(name="c_mobile")
    private String mobile;

    @Column(name="d_create_at")
    private Date createAt;

    @Column(name = "d_read_at")
    private Date readAt;

    @Column(name="c_status")
    private String status;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getReadAt() {
        return readAt;
    }

    public void setReadAt(Date readAt) {
        this.readAt = readAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

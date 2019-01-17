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
@Table(name="t_hpp_notice")
public class HppNotice extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="c_type")
    private String type;  //消息类型

    @Column(name="c_context")
    private String context; //消息内容

    @Column(name="c_url")
    private String url; //跳转url

    @Column(name="c_create_by")
    private String createBy; //创建人

    @Column(name="d_create_time")
    private Date createTime;

    @Column(name = "d_update_time")
    private Date updateTime;

    @Column(name="c_send_flag")
    private String sendFlag;    //标记是否已发布

    @Column(name="c_revoke_flag")
    private String revokeFlag;    //标记是否已撤销

    @Column(name="c_object")
    private String object;    //通知对象类型

    @Column(name = "d_send_time")
    private Date sendTime;    //发送时间

    @Column(name = "d_revoke_time")
    private Date revokeTime;    //撤销时间

    @Column(name = "c_del_flag")
    private String delFlag;    //标记是否删除

    @Column(name = "d_del_time")
    private Date delTime;    //撤销时间



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getRevokeFlag() {
        return revokeFlag;
    }

    public void setRevokeFlag(String revokeFlag) {
        this.revokeFlag = revokeFlag;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getRevokeTime() {
        return revokeTime;
    }

    public void setRevokeTime(Date revokeTime) {
        this.revokeTime = revokeTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

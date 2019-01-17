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
@Table(name="t_hpp_integral_detail")
public class HppIntegralDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="c_mobile")
    private String mobile;

    @Column(name="d_create_time")
    private Date createTime;

    @Column(name="c_state")
    private String state;   //in or out

    @Column(name="c_type")
    private String type;   //friend community video course strategy

    @Column(name="i_score")
    private Double score;

    @Column(name="c_uuid")
    private String uuid;   //微信识别id

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}

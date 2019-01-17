package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * A 兑换明细.
 */
@Entity
@Table(name = "t_hpp_exchange")
public class HppExchange extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_mobile_num")
    private String mobileNum;

    @Column(name = "c_body")
    private String body;   //兑换类型

    @Column(name = "c_body_id")
    private String bodyId;

    @Column(name = "i_price")
    private Double price;       //兑换时积分价格

    @Column(name = "d_create_at")
    private Date createAt;

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyId() {
        return bodyId;
    }

    public void setBodyId(String bodyId) {
        this.bodyId = bodyId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}

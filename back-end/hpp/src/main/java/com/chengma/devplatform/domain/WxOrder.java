package com.chengma.devplatform.domain;

/**
 * Created by Administrator on 2018/8/9.
 */

import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "t_wx_order")
public class WxOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_body")
    private String body;     //商品描述

    @Column(name = "c_body_name")
    private String bodyName;     //商品名称

    @Column(name = "c_body_id")
    private String bodyId;     //商品id

    @Column(name = "c_body_parent_id")
    private String bodyParentId;     //商品集id

    @Column(name = "c_body_detail")
    private String bodyDetail;     //商品详情

    @Column(name = "c_out_trade_no")
    private String outTradeNo;     //商户订单号

    @Column(name = "c_pay_type")
    private String payType;     //支付方式

    @Column(name = "c_fee_type")
    private String feeType;    //货币类型

    @Column(name = "i_total_fee")
    private Double totalFee;    //微信总金额

    @Column(name = "i_integral")
    private Double integral;    //使用积分

    @Column(name = "i_pay")
    private Double pay;    //总金额

    @Column(name = "c_spbill_create_ip")
    private String spbillCreateIp;    //用户端实际ip

    @Column(name = "d_time_start")
    private Date timeStart;

    @Column(name = "d_time_expire")
    private Date timeExpire;

    @Column(name = "c_object")
    private String object;    //操作用户

    @Column(name = "c_status")
    private String status;    //N 未付款 Y已付款  DISABLE订单失效

    @Column(name = "c_wx_pay_order_string")
    private String wxPayOrderString;

    @Column(name = " c_body_img")
    private String bodyImg;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getBodyName() {
        return bodyName;
    }

    public void setBodyName(String bodyName) {
        this.bodyName = bodyName;
    }

    public String getBodyDetail() {
        return bodyDetail;
    }

    public void setBodyDetail(String bodyDetail) {
        this.bodyDetail = bodyDetail;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(Date timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBodyId() {
        return bodyId;
    }

    public void setBodyId(String bodyId) {
        this.bodyId = bodyId;
    }

    public String getWxPayOrderString() {
        return wxPayOrderString;
    }

    public void setWxPayOrderString(String wxPayOrderString) {
        this.wxPayOrderString = wxPayOrderString;
    }

    public String getBodyImg() {
        return bodyImg;
    }

    public void setBodyImg(String bodyImg) {
        this.bodyImg = bodyImg;
    }

    public Double getIntegral() {
        return integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public String getBodyParentId() {
        return bodyParentId;
    }

    public void setBodyParentId(String bodyParentId) {
        this.bodyParentId = bodyParentId;
    }
}

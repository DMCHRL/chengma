package com.chengma.devplatform.service.dto;

import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/25.
 */
public class WxOrderDTO {

    private String id;

    private String body;     //商品描述

    private String outTradeNo;     //商户订单号

    private String bodyName;     //商品名称

    private String bodyDetail;     //商品详情

    private String bodyId;     //商品描述

    private String payType;     //支付方式

    private String feeType;    //货币类型

    private Double totalFee;    //总金额

    private Double integral;    //使用积分

    private String spbillCreateIp;    //用户端实际ip

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date timeStart;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date timeExpire;

    private String object;    //操作用户

    private String wxPayOrderString; //预付信息

    private String status;    //支付状态 Y  N   disable(失效)

    private String bodyImg; //商品图片

    private Double pay;    //总金额

    private Integer total; //总人数

    private Integer monthTotal; //近30天下单人数

    private String bodyParentId;     //商品集id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getWxPayOrderString() {
        return wxPayOrderString;
    }

    public void setWxPayOrderString(String wxPayOrderString) {
        this.wxPayOrderString = wxPayOrderString;
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

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public Double getPay() {
        return pay;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getMonthTotal() {
        return monthTotal;
    }

    public void setMonthTotal(Integer monthTotal) {
        this.monthTotal = monthTotal;
    }

    public String getBodyParentId() {
        return bodyParentId;
    }

    public void setBodyParentId(String bodyParentId) {
        this.bodyParentId = bodyParentId;
    }
}

package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import java.util.Date;

/**
 * A DTO for the SysUserRole entity.
 */
public class LlPayOrderDTO {

    private String id;

    private String noOrder;

    private String dtOrder;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createAt;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date updateAt;

    private String moneyOrder;

    private String nameGoods;

    private String flagPayProduct;  //0 快捷、1 认证、2 网银、5新认证支付

    private String flagChnl;    //0 App-Android。1App-iOS。2 Web。3 H5

    private String userId;     //用户id

    private String cardNo;   //银行卡号

    private String acctName;        //银行卡号用户名

    private String cardType;        //0， 借记卡。 1，信用卡。 2， 企业网银。

    private String payStatus;        //0 未付 1已付 2失效

    private String gatewayUrl;       //预付链接

    private String idNo;              //身份证号码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoOrder() {
        return noOrder;
    }

    public void setNoOrder(String noOrder) {
        this.noOrder = noOrder;
    }

    public String getDtOrder() {
        return dtOrder;
    }

    public void setDtOrder(String dtOrder) {
        this.dtOrder = dtOrder;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getMoneyOrder() {
        return moneyOrder;
    }

    public void setMoneyOrder(String moneyOrder) {
        this.moneyOrder = moneyOrder;
    }

    public String getNameGoods() {
        return nameGoods;
    }

    public void setNameGoods(String nameGoods) {
        this.nameGoods = nameGoods;
    }

    public String getFlagPayProduct() {
        return flagPayProduct;
    }

    public void setFlagPayProduct(String flagPayProduct) {
        this.flagPayProduct = flagPayProduct;
    }

    public String getFlagChnl() {
        return flagChnl;
    }

    public void setFlagChnl(String flagChnl) {
        this.flagChnl = flagChnl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }


}

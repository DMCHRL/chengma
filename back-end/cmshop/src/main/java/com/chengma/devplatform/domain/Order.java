package com.chengma.devplatform.domain;

import com.chengma.devplatform.common.entity.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * A TranInfo.
 */
@Entity
@Table(name = "t_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_no_order")
    private String noOrder;

    @Column(name = "d_dt_order")
    private String dtOrder;

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "d_update_at")
    private Date updateAt;

    @Column(name = "i_money_order")
    private String moneyOrder;

    @Column(name = "c_name_goods")
    private String nameGoods;

    @Column(name = "c_flag_pay_product")
    private String flagPayProduct;  //0 快捷、1 认证、2 网银、5新认证支付

    @Column(name = "c_flag_chnl")
    private String flagChnl;    //0 App-Android。1App-iOS。2 Web。3 H5

    @Column(name = "c_user_id")
    private String userId;     //用户id

    @Column(name = "c_product_id")
    private String productId;  //商品Id

    @Column(name = "c_card_no")
    private String cardNo;   //银行卡号

    @Column(name = "c_acct_name")
    private String acctName;        //银行卡号用户名

    @Column(name = "c_card_type")
    private String cardType;        //0， 借记卡。 1，信用卡。 2， 企业网银。

    @Column(name = "c_pay_status")
    private String payStatus;        //0 未付 1已付 2失效

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}

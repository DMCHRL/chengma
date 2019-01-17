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
@Table(name = "t_usdt_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UsdtOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "p_order_id")
    private String P_OrderId;  //商户订单号

    @Column(name = "p_card_id")
    private String P_CardId;    //卡类充值时的卡号

    @Column(name = "p_card_pass")
    private String P_CardPass;    //卡类充值时的卡密

    @Column(name = "p_face_value")
    private float P_FaceValue;    ////面值/订单支付金额

    @Column(name = "p_channel_id")
    private int P_ChannelId;     //支付币种 3 Bitcoin比特币, 6 Litecoin莱特币, 7 Tether泰达币

    @Column(name = "p_subject")
    private String P_Subject;    //产品名称

    @Column(name = "p_price")
    private float P_Price;    //产品价格

    @Column(name = "p_quantity")
    private int P_Quantity;    //产品数量

    @Column(name = "p_description")
    private String P_Description;    //产品描述

    @Column(name = "p_notic")
    private String P_Notic;    //用户附加信息

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "d_update_at")
    private Date updateAt;

    public String getP_OrderId() {
        return P_OrderId;
    }

    public void setP_OrderId(String p_OrderId) {
        P_OrderId = p_OrderId;
    }

    public String getP_CardId() {
        return P_CardId;
    }

    public void setP_CardId(String p_CardId) {
        P_CardId = p_CardId;
    }

    public String getP_CardPass() {
        return P_CardPass;
    }

    public void setP_CardPass(String p_CardPass) {
        P_CardPass = p_CardPass;
    }


    public int getP_ChannelId() {
        return P_ChannelId;
    }

    public void setP_ChannelId(int p_ChannelId) {
        P_ChannelId = p_ChannelId;
    }

    public String getP_Subject() {
        return P_Subject;
    }

    public void setP_Subject(String p_Subject) {
        P_Subject = p_Subject;
    }

    public float getP_FaceValue() {
        return P_FaceValue;
    }

    public void setP_FaceValue(float p_FaceValue) {
        P_FaceValue = p_FaceValue;
    }

    public float getP_Price() {
        return P_Price;
    }

    public void setP_Price(float p_Price) {
        P_Price = p_Price;
    }

    public int getP_Quantity() {
        return P_Quantity;
    }

    public void setP_Quantity(int p_Quantity) {
        P_Quantity = p_Quantity;
    }

    public String getP_Description() {
        return P_Description;
    }

    public void setP_Description(String p_Description) {
        P_Description = p_Description;
    }

    public String getP_Notic() {
        return P_Notic;
    }

    public void setP_Notic(String p_Notic) {
        P_Notic = p_Notic;
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
}

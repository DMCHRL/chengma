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

    @Column(name = "c_order_id")
    private String orderId;  //商户订单号

    @Column(name = "i_face_value")
    private float faceValue;    //面值/订单支付金额

    @Column(name = "i_usd")
    private float usd;    //美元

    @Column(name = "i_channel_id")
    private int channelId;     //支付币种 3 Bitcoin比特币, 6 Litecoin莱特币, 7 Tether泰达币

    @Column(name = "c_account")
    private String account;    //交易账号

    @Column(name = "c_user_id")
    private String userId;    //用户id

    @Column(name = "c_notic")
    private String notic;    //用户附加信息

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "d_update_at")
    private Date updateAt;

    @Column(name = "c_status")
    private String status;    //支付状态

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public float getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(float faceValue) {
        this.faceValue = faceValue;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public float getUsd() {
        return usd;
    }

    public void setUsd(float usd) {
        this.usd = usd;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNotic() {
        return notic;
    }

    public void setNotic(String notic) {
        this.notic = notic;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

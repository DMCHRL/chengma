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
 * A 数字货币通道
 */
@Entity
@Table(name = "t_digital_currency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DigitalCurrency extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_user_id")
    private String userId;      //商户号

    @Column(name = "c_channel_key")
    private String channelKey;  //密钥

    @Column(name = "c_pay_url")
    private String payUrl;

    @Column(name = "c_result_url")
    private String resultUrl;    //异步通知

    @Column(name = "c_notify_url")
    private String notifyUrl;    //同步通知

    @Column(name = "c_flag")
    private String flag;    //同步通知

    @Column(name = "c_channel_id")
    private Integer channelId;    //货币类型

    @Column(name = "i_usd_rate")
    private Integer usdRate;    //美元汇率

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "d_update_at")
    private Date updateAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannelKey() {
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getUsdRate() {
        return usdRate;
    }

    public void setUsdRate(Integer usdRate) {
        this.usdRate = usdRate;
    }
}

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
@Table(name = "t_bind_bank")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BindBank extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_appid")
    private String appid;

    @Column(name = "c_mch_id")
    private String mchId;

    @Column(name = "c_banksn")
    private String banksn;

    @Column(name = "c_khname")
    private String khname;

    @Column(name = "c_idtype")
    private String idtype;

    @Column(name = "c_idcardsn")
    private String idcardsn;

    @Column(name = "c_mobile")
    private String mobile;

    @Column(name = "c_bind_success")
    private String bindSuccess;

    @Column(name = "c_bind_result")
    private String bindResult;

    @Column(name = "d_create_at")
    private Date createDt;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getBanksn() {
        return banksn;
    }

    public void setBanksn(String banksn) {
        this.banksn = banksn;
    }

    public String getKhname() {
        return khname;
    }

    public void setKhname(String khname) {
        this.khname = khname;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getIdcardsn() {
        return idcardsn;
    }

    public void setIdcardsn(String idcardsn) {
        this.idcardsn = idcardsn;
    }

    public String getBindSuccess() {
        return bindSuccess;
    }

    public void setBindSuccess(String bindSuccess) {
        this.bindSuccess = bindSuccess;
    }

    public String getBindResult() {
        return bindResult;
    }

    public void setBindResult(String bindResult) {
        this.bindResult = bindResult;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

package com.chengma.devplatform.service.dto;

import java.util.Date;

/**
 * Created by Administrator on 2018/6/25.
 */
public class BindBankDTO {

    private String id;

    private String appid;

    private String mchId;

    private String banksn;

    private String khname;

    private String idtype;

    private String idcardsn;


    private String mobile;

    private String bindSuccess;

    private String bindResult;

    private Date createDt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "BindBankDTO{" +
                "id='" + id + '\'' +
                ", appid='" + appid + '\'' +
                ", mchId='" + mchId + '\'' +
                ", banksn='" + banksn + '\'' +
                ", khname='" + khname + '\'' +
                ", idtype='" + idtype + '\'' +
                ", idcardsn='" + idcardsn + '\'' +
                ", bindSuccess='" + bindSuccess + '\'' +
                ", bindResult='" + bindResult + '\'' +
                ", createDt=" + createDt +
                '}';
    }
}

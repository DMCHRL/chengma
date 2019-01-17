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
 * A MobileValidate.
 */
@Entity
@Table(name = "t_hpp_mobile_validate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HppMobileValidate extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_mobile_num")
    private String mobileNum;

    @Column(name = "c_validate_code")
    private String validateCode;

    @Column(name = "d_failure_time")
    private Date failureTime;

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "d_last_update_at")
    private Date lastUpdateAt;

    @Column(name = "c_create_by")
    private String createBy;

    @Column(name = "c_last_update_by")
    private String lastUpdateBy;

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public Date getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(Date failureTime) {
        this.failureTime = failureTime;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(Date lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}

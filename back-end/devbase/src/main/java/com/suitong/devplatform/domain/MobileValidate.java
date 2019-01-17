package com.suitong.devplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A MobileValidate.
 */
@Entity
@Table(name = "t_mobile_validate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MobileValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "i_create_by")
    private Long createBy;

    @Column(name = "i_last_update_by")
    private Long lastUpdateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public MobileValidate mobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
        return this;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public MobileValidate validateCode(String validateCode) {
        this.validateCode = validateCode;
        return this;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public Date getFailureTime() {
        return failureTime;
    }

    public MobileValidate failureTime(Date failureTime) {
        this.failureTime = failureTime;
        return this;
    }

    public void setFailureTime(Date failureTime) {
        this.failureTime = failureTime;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public MobileValidate createAt(Date createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getLastUpdateAt() {
        return lastUpdateAt;
    }

    public MobileValidate lastUpdateAt(Date lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
        return this;
    }

    public void setLastUpdateAt(Date lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public MobileValidate createBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }

    public MobileValidate lastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
        return this;
    }

    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MobileValidate mobileValidate = (MobileValidate) o;
        if (mobileValidate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mobileValidate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MobileValidate{" +
            "id=" + getId() +
            ", mobileNum='" + getMobileNum() + "'" +
            ", validateCode='" + getValidateCode() + "'" +
            ", failureTime='" + getFailureTime() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", lastUpdateAt='" + getLastUpdateAt() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", lastUpdateBy='" + getLastUpdateBy() + "'" +
            "}";
    }
}

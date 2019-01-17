package com.suitong.devplatform.service.dto;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO for the MobileValidate entity.
 */
public class MobileValidateDTO implements Serializable {

    private Long id;

    private String mobileNum;

    private String validateCode;

    private Date failureTime;

    private Date createAt;

    private Date lastUpdateAt;

    private Long createBy;

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

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getLastUpdateBy() {
        return lastUpdateBy;
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

        MobileValidateDTO mobileValidateDTO = (MobileValidateDTO) o;
        if(mobileValidateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mobileValidateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MobileValidateDTO{" +
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

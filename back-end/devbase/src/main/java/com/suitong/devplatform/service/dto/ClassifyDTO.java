package com.suitong.devplatform.service.dto;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO for the SingleItemType entity.
 */
public class ClassifyDTO implements Serializable {

    private Long id;

    /*分类名称*/
    private String classifyName;
    /*分类归属*/
    private Long classifyBelong;
    /*分类排序*/
    private Long classifySort;
    /*所属店铺*/
    private String storeCode;
    /*创建人*/
    private Long createBy;
    /*创建时间*/
    private Date createAt;
    /*最后更新人*/
    private Long lastUpdateBy;
    /*最后更新时间*/
    private Date lastUpdateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Long getClassifyBelong() {
        return classifyBelong;
    }

    public void setClassifyBelong(Long classifyBelong) {
        this.classifyBelong = classifyBelong;
    }

    public Long getClassifySort() {
        return classifySort;
    }

    public void setClassifySort(Long classifySort) {
        this.classifySort = classifySort;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(Date lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassifyDTO classifyDTO = (ClassifyDTO) o;
        if(classifyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classifyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SingleItemTypeDTO{" +
            "id=" + getId() +
            ", classifyName='" + getClassifyName() + "'" +
            ", classifyBelong='" + getClassifyBelong() + "'" +
            ", classifySort='" + getClassifySort() + "'" +
            ", storeCode='" + getStoreCode() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", lastUpdateBy='" + getLastUpdateBy() + "'" +
            ", lastUpdateAt='" + getLastUpdateAt() + "'" +
            "}";
    }
}

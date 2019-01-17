package com.suitong.devplatform.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A SingleItemType.
 */
@Entity
@Table(name = "single_item_type")
public class Classify implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_classify_name")
    private String classifyName;

    @Column(name = "i_classify_belong")
    private Long classifyBelong;

    @Column(name = "i_classify_sort")
    private Long classifySort;

    @Column(name = "c_store_code")
    private String storeCode;

    @Column(name = "i_create_by")
    private Long createBy;

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "i_last_update_by")
    private Long lastUpdateBy;

    @Column(name = "d_last_update_at")
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

    public Classify classifyName(String classifyName) {
        this.classifyName = classifyName;
        return this;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Long getClassifyBelong() {
        return classifyBelong;
    }

    public Classify classifyBelong(Long classifyBelong) {
        this.classifyBelong = classifyBelong;
        return this;
    }

    public void setClassifyBelong(Long classifyBelong) {
        this.classifyBelong = classifyBelong;
    }

    public Long getClassifySort() {
        return classifySort;
    }

    public Classify classifySort(Long classifySort) {
        this.classifySort = classifySort;
        return this;
    }

    public void setClassifySort(Long classifySort) {
        this.classifySort = classifySort;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public Classify storeCode(String storeCode) {
        this.storeCode = storeCode;
        return this;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public Classify createBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Classify createAt(Date createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }

    public Classify lastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
        return this;
    }

    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateAt() {
        return lastUpdateAt;
    }

    public Classify lastUpdateAt(Date lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
        return this;
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
        Classify classify = (Classify) o;
        if (classify.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classify.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SingleItemType{" +
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

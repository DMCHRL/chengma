package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * A SingleItemType.
 */
@Entity
@Table(name = "single_item_type")
public class Classify extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_classify_name")
    private String classifyName;

    @Column(name = "i_classify_belong")
    private Long classifyBelong;

    @Column(name = "i_classify_sort")
    private Long classifySort;

    @Column(name = "c_store_code")
    private String storeCode;

    @Column(name = "c_create_by")
    private String createBy;

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "c_last_update_by")
    private String lastUpdateBy;

    @Column(name = "d_last_update_at")
    private Date lastUpdateAt;

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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(Date lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }
}

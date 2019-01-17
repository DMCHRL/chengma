package com.chengma.devplatform.service.dto;


import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the SingleItemType entity.
 */
public class ClassifyDTO implements Serializable {

    private String id;

    /**
     * 分类名称
     */
    private String classifyName;

    /**
     * 分类归属
     */
    private Long classifyBelong;

    /**
     * 分类排序
     */
    private Long classifySort;

    /**
     * 所属店铺
     */
    private String storeCode;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 最后更新人
     */
    private String lastUpdateBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

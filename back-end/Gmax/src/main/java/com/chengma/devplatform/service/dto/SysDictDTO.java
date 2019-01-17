package com.chengma.devplatform.service.dto;


import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the SysDict entity.
 */
public class SysDictDTO implements Serializable {

    private String id;

    private String dictParent;

    private String dictCode;

    private String dictItem;

    private Long sort;

    private String delFlag;

    private String createBy;

    private Date createAt;

    private String lastUpdateBy;

    private Date lastUpdateAt;

    private String parentName; //父节点名称

    private String childrenCount;

    private String dictKey;

    private String dictDesc;

    private String group;

    private String parentGroup;

    private Long maxSort;

    private String dictShop;

    private String status;

    private String showName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDictParent() {
        return dictParent;
    }

    public void setDictParent(String dictParent) {
        this.dictParent = dictParent;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictItem() {
        return dictItem;
    }

    public void setDictItem(String dictItem) {
        this.dictItem = dictItem;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(String childrenCount) {
        this.childrenCount = childrenCount;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(String parentGroup) {
        this.parentGroup = parentGroup;
    }

    public Long getMaxSort() {
        return maxSort;
    }

    public void setMaxSort(Long maxSort) {
        this.maxSort = maxSort;
    }

    public String getDictShop() {
        return dictShop;
    }

    public void setDictShop(String dictShop) {
        this.dictShop = dictShop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }
}

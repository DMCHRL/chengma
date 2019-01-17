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
 * A SysDict.
 */
@Entity
@Table(name = "t_sys_dict")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysDict extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_dict_parent")
    private String dictParent;

    @Column(name = "c_dict_code")
    private String dictCode;

    @Column(name = "c_dict_item")
    private String dictItem;

    @Column(name = "i_sort")
    private Long sort;

    @Column(name = "c_del_flag")
    private String delFlag;

    @Column(name = "c_create_by")
    private String createBy;

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "c_last_update_by")
    private String lastUpdateBy;

    @Column(name = "d_last_update_at")
    private Date lastUpdateAt;

    @Column(name = "c_dict_key")
    private String dictKey;

    @Column(name = "c_dict_desc")
    private String dictDesc;

    @Column(name = "c_group")
    private String group;

    @Column(name = "c_parent_group")
    private String parentGroup;

    @Column(name = "c_dict_shop")
    private String dictShop;

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

    public String getDictShop() {
        return dictShop;
    }

    public void setDictShop(String dictShop) {
        this.dictShop = dictShop;
    }
}

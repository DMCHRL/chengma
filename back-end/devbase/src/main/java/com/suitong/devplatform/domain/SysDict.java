package com.suitong.devplatform.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * A SysDict.
 */
@Entity
@Table(name = "t_sys_dict")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "i_dict_parent")
    private Long dictParent;

    @Column(name = "c_dict_code")
    private String dictCode;

    @Column(name = "c_dict_item")
    private String dictItem;

    @Column(name = "i_sort")
    private Long sort;

    @Column(name = "c_del_flag")
    private String delFlag;

    @Column(name = "i_create_by")
    private Long createBy;

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "i_last_update_by")
    private Long lastUpdateBy;

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

    public String getDictShop() {
        return dictShop;
    }

    public void setDictShop(String dictShop) {
        this.dictShop = dictShop;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDictParent() {
        return dictParent;
    }

    public SysDict dictParent(Long dictParent) {
        this.dictParent = dictParent;
        return this;
    }

    public void setDictParent(Long dictParent) {
        this.dictParent = dictParent;
    }

    public String getDictCode() {
        return dictCode;
    }

    public SysDict dictCode(String dictCode) {
        this.dictCode = dictCode;
        return this;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictItem() {
        return dictItem;
    }

    public SysDict dictItem(String dictItem) {
        this.dictItem = dictItem;
        return this;
    }

    public void setDictItem(String dictItem) {
        this.dictItem = dictItem;
    }

    public Long getSort() {
        return sort;
    }

    public SysDict sort(Long sort) {
        this.sort = sort;
        return this;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public SysDict delFlag(String delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public SysDict createBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public SysDict createAt(Date createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }

    public SysDict lastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
        return this;
    }

    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateAt() {
        return lastUpdateAt;
    }

    public SysDict lastUpdateAt(Date lastUpdateAt) {
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
        SysDict sysDict = (SysDict) o;
        if (sysDict.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysDict.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysDict{" +
            "id=" + getId() +
            ", dictParent='" + getDictParent() + "'" +
            ", dictCode='" + getDictCode() + "'" +
            ", dictItem='" + getDictItem() + "'" +
            ", sort='" + getSort() + "'" +
            ", delFlag='" + getDelFlag() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", lastUpdateBy='" + getLastUpdateBy() + "'" +
            ", lastUpdateAt='" + getLastUpdateAt() + "'" +
            "}";
    }
}

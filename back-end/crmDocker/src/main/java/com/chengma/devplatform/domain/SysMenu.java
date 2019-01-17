package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A SysMenu.
 */
@Entity
@Table(name = "t_sys_menu")
public class SysMenu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_menu_no")
    private String menuNo;

    @Column(name = "c_menu_name")
    private String menuName;

    @Column(name = "c_english_name")
    private String englishName;

    @Column(name = "c_url")
    private String url;

    @Column(name = "c_icon")
    private String icon;

    @Column(name = "n_sort")
    private Integer sort;

    @Column(name = "c_visible")
    private String visible;

    @Column(name = "c_access_edit")
    private String accessEdit;

    @Column(name = "c_parent_id")
    private String parentId;

    @Column(name = "c_all_check")
    private String allCheck;

    @Column(name = "c_del_flag")
    private String delFlag;

    @Column(name = "c_lang_switch")
    private String langSwitch;

    public String getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(String menuNo) {
        this.menuNo = menuNo;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getAccessEdit() {
        return accessEdit;
    }

    public void setAccessEdit(String accessEdit) {
        this.accessEdit = accessEdit;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAllCheck() {
        return allCheck;
    }

    public void setAllCheck(String allCheck) {
        this.allCheck = allCheck;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLangSwitch() {
        return langSwitch;
    }

    public void setLangSwitch(String langSwitch) {
        this.langSwitch = langSwitch;
    }
}

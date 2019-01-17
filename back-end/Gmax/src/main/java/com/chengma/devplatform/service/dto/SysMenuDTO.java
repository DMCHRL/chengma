package com.chengma.devplatform.service.dto;


import java.io.Serializable;

/**
 * A DTO for the SysMenu entity.
 */
public class SysMenuDTO implements Serializable {

    private String id;

    private String menuNo;

    private String menuName;

    private String englishName;

    private String url;

    private String icon;

    private Integer sort;

    private String visible;

    private String accessEdit;

    private String parentId;

    private String parentName;

    private String allCheck;

    private String delFlag;

    private String langSwitch;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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

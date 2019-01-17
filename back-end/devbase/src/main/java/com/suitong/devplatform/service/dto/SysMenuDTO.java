package com.suitong.devplatform.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SysMenu entity.
 */
public class SysMenuDTO implements Serializable {

    private Long id;

    private String menuNo;

    private String menuName;

    private String englishName;

    private String url;

    private String icon;

    private Integer sort;

    private String visible;

    private String accessEdit;

    private Long parentId;

    private String parentName;

    private String allCheck;

    private String delFlag;

    private String langSwitch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(String menuNo) {
        this.menuNo = menuNo;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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

    public String getAccessEdit() {
        return accessEdit;
    }

    public void setAccessEdit(String accessEdit) {
        this.accessEdit = accessEdit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAllCheck() {
        return allCheck;
    }

    public void setAllCheck(String allCheck) {
        this.allCheck = allCheck;
    }

    public String getLangSwitch() {
        return langSwitch;
    }

    public void setLangSwitch(String langSwitch) {
        this.langSwitch = langSwitch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysMenuDTO sysMenuDTO = (SysMenuDTO) o;
        if(sysMenuDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysMenuDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysMenuDTO{" +
            "id=" + getId() +
            ", menuNo='" + getMenuNo() + "'" +
            ", menuName='" + getMenuName() + "'" +
            ", url='" + getUrl() + "'" +
            ", sort='" + getSort() + "'" +
            ", visible='" + getVisible() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", allCheck='" + getAllCheck() + "'" +
            "}";
    }
}

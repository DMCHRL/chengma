package com.suitong.devplatform.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SysMenu.
 */
@Entity
@Table(name = "t_sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "i_parent_id")
    private Long parentId;

    @Column(name = "c_all_check")
    private String allCheck;

    @Column(name = "c_del_flag")
    private String delFlag;

    @Column(name = "c_lang_switch")
    private String langSwitch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuNo() {
        return menuNo;
    }

    public SysMenu menuNo(String menuNo) {
        this.menuNo = menuNo;
        return this;
    }

    public String getEnglishName() {
        return englishName;
    }

    public SysMenu englishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public void setMenuNo(String menuNo) {
        this.menuNo = menuNo;
    }

    public String getMenuName() {
        return menuName;
    }

    public SysMenu menuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getUrl() {
        return url;
    }

    public SysMenu url(String url) {
        this.url = url;
        return this;
    }

    public String getAccessEdit() {
        return accessEdit;
    }

    public void setAccessEdit(String accessEdit) {
        this.accessEdit = accessEdit;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public SysMenu sort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getVisible() {
        return visible;
    }

    public SysMenu visible(String visible) {
        this.visible = visible;
        return this;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public Long getParentId() {
        return parentId;
    }

    public SysMenu parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAllCheck() {
        return allCheck;
    }

    public SysMenu allCheck(String allCheck) {
        this.allCheck = allCheck;
        return this;
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
        SysMenu sysMenu = (SysMenu) o;
        if (sysMenu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysMenu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysMenu{" +
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

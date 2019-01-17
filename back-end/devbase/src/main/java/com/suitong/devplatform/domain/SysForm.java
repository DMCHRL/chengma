package com.suitong.devplatform.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SysForm.
 */
@Entity
@Table(name = "t_sys_form")
public class SysForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_form_name")
    private String formName;

    @Column(name = "c_english_name")
    private String englishName;

    @Column(name = "i_menu_id")
    private Long menuId;

    @Column(name = "c_access_edit")
    private String accessEdit;

    @Column(name = "c_visible")
    private String visible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormName() {
        return formName;
    }

    public SysForm formName(String formName) {
        this.formName = formName;
        return this;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public SysForm englishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Long getMenuId() {
        return menuId;
    }

    public SysForm menuId(Long menuId) {
        this.menuId = menuId;
        return this;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getAccessEdit() {
        return accessEdit;
    }

    public SysForm accessEdit(String accessEdit) {
        this.accessEdit = accessEdit;
        return this;
    }

    public void setAccessEdit(String accessEdit) {
        this.accessEdit = accessEdit;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysForm sysForm = (SysForm) o;
        if (sysForm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysForm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysForm{" +
            "id=" + getId() +
            ", formName='" + getFormName() + "'" +
            ", englishName='" + getEnglishName() + "'" +
            ", menuId='" + getMenuId() + "'" +
            ", accessEdit='" + getAccessEdit() + "'" +
            "}";
    }
}

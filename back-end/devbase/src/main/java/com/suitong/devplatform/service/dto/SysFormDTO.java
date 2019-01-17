package com.suitong.devplatform.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SysForm entity.
 */
public class SysFormDTO implements Serializable {

    private Long id;

    private String formName;

    private String englishName;

    private Long menuId;

    private String accessEdit;

    private boolean checked;

    private String visible;

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getAccessEdit() {
        return accessEdit;
    }

    public void setAccessEdit(String accessEdit) {
        this.accessEdit = accessEdit;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysFormDTO sysFormDTO = (SysFormDTO) o;
        if(sysFormDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysFormDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysFormDTO{" +
            "id=" + getId() +
            ", formName='" + getFormName() + "'" +
            ", englishName='" + getEnglishName() + "'" +
            ", menuId='" + getMenuId() + "'" +
            ", accessEdit='" + getAccessEdit() + "'" +
            "}";
    }
}

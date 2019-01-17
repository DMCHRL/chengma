package com.suitong.devplatform.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SysComponent entity.
 */
public class SysComponentDTO implements Serializable {

    private Long id;

    private String componentName;

    private String englishName;

    private Long formId;

    private String visible;

    private String accessEdit;

    private boolean checked;

    private String formEnglishName;

    public String getFormEnglishName() {
        return formEnglishName;
    }

    public void setFormEnglishName(String formEnglishName) {
        this.formEnglishName = formEnglishName;
    }

    public String getAccessEdit() {
        return accessEdit;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
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

        SysComponentDTO sysComponentDTO = (SysComponentDTO) o;
        if(sysComponentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysComponentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysComponentDTO{" +
            "id=" + getId() +
            ", componentName='" + getComponentName() + "'" +
            ", englishName='" + getEnglishName() + "'" +
            ", formId='" + getFormId() + "'" +
            "}";
    }
}

package com.suitong.devplatform.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SysComponent.
 */
@Entity
@Table(name = "t_sys_component")
public class SysComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_component_name")
    private String componentName;

    @Column(name = "c_english_name")
    private String englishName;

    @Column(name = "i_form_id")
    private Long formId;

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

    public String getComponentName() {
        return componentName;
    }

    public SysComponent componentName(String componentName) {
        this.componentName = componentName;
        return this;
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

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public SysComponent englishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Long getFormId() {
        return formId;
    }

    public SysComponent formId(Long formId) {
        this.formId = formId;
        return this;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysComponent sysComponent = (SysComponent) o;
        if (sysComponent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysComponent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysComponent{" +
            "id=" + getId() +
            ", componentName='" + getComponentName() + "'" +
            ", englishName='" + getEnglishName() + "'" +
            ", formId='" + getFormId() + "'" +
            "}";
    }
}

package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A SysComponent.
 */
@Entity
@Table(name = "t_sys_component")
public class SysComponent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_component_name")
    private String componentName;

    @Column(name = "c_english_name")
    private String englishName;

    @Column(name = "c_form_id")
    private String formId;

    @Column(name = "c_access_edit")
    private String accessEdit;

    @Column(name = "c_visible")
    private String visible;

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

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
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

}

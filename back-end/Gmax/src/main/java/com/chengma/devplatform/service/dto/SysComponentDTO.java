package com.chengma.devplatform.service.dto;


import java.io.Serializable;

/**
 * A DTO for the SysComponent entity.
 */
public class SysComponentDTO implements Serializable {

    private String id;

    private String componentName;

    private String englishName;

    private String formId;

    private String visible;

    private String accessEdit;

    private boolean checked;

    private String formEnglishName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getFormEnglishName() {
        return formEnglishName;
    }

    public void setFormEnglishName(String formEnglishName) {
        this.formEnglishName = formEnglishName;
    }
}

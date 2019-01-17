package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A SysForm.
 */
@Entity
@Table(name = "t_sys_form")
public class SysForm extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_form_name")
    private String formName;

    @Column(name = "c_english_name")
    private String englishName;

    @Column(name = "c_menu_id")
    private String menuId;

    @Column(name = "c_access_edit")
    private String accessEdit;

    @Column(name = "c_visible")
    private String visible;

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

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
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

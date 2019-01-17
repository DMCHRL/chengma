package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A SysRoleForm.
 */
@Entity
@Table(name = "t_sys_role_form")
public class SysRoleForm extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_sys_role_id")
    private String sysRoleId;

    @Column(name = "c_sys_form_id")
    private String sysFormId;

    public String getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(String sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public String getSysFormId() {
        return sysFormId;
    }

    public void setSysFormId(String sysFormId) {
        this.sysFormId = sysFormId;
    }
}

package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A SysRoleComponent.
 */
@Entity
@Table(name = "t_sys_role_component")
public class SysRoleComponent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_sys_role_id")
    private String sysRoleId;

    @Column(name = "c_sys_component_id")
    private String sysComponentId;

    public String getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(String sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public String getSysComponentId() {
        return sysComponentId;
    }

    public void setSysComponentId(String sysComponentId) {
        this.sysComponentId = sysComponentId;
    }
}

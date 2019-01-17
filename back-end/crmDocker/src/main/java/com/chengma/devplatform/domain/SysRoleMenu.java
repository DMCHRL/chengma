package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A SysRoleMenu.
 */
@Entity
@Table(name = "t_sys_role_menu")
public class SysRoleMenu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_sys_role_id")
    private String sysRoleId;

    @Column(name = "c_sys_menu_id")
    private String sysMenuId;

    public String getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(String sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public String getSysMenuId() {
        return sysMenuId;
    }

    public void setSysMenuId(String sysMenuId) {
        this.sysMenuId = sysMenuId;
    }
}

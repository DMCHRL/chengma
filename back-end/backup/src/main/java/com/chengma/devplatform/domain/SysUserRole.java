package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A SysUserRole.
 */
@Entity
@Table(name = "t_sys_user_role")
public class SysUserRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_sys_roles_id")
    private String sysRolesId;

    @Column(name = "c_sys_users_id")
    private String sysUsersId;

    public String getSysRolesId() {
        return sysRolesId;
    }

    public void setSysRolesId(String sysRolesId) {
        this.sysRolesId = sysRolesId;
    }

    public String getSysUsersId() {
        return sysUsersId;
    }

    public void setSysUsersId(String sysUsersId) {
        this.sysUsersId = sysUsersId;
    }
}

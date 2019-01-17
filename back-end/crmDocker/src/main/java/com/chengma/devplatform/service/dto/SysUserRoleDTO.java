package com.chengma.devplatform.service.dto;


import java.io.Serializable;

/**
 * A DTO for the SysUserRole entity.
 */
public class SysUserRoleDTO implements Serializable {

    private String id;

    private String sysRolesId;

    private String sysUsersId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

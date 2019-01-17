package com.chengma.devplatform.service.dto;


import java.io.Serializable;

/**
 * A DTO for the SysRoleMenu entity.
 */
public class SysRoleMenuDTO implements Serializable {

    private String id;

    private String sysRoleId;

    private String sysMenuId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

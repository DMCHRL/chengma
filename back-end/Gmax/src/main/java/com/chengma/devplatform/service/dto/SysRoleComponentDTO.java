package com.chengma.devplatform.service.dto;


import java.io.Serializable;

/**
 * A DTO for the SysRoleComponent entity.
 */
public class SysRoleComponentDTO implements Serializable {

    private String id;

    private String sysRoleId;

    private String sysComponentId;

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

    public String getSysComponentId() {
        return sysComponentId;
    }

    public void setSysComponentId(String sysComponentId) {
        this.sysComponentId = sysComponentId;
    }
}

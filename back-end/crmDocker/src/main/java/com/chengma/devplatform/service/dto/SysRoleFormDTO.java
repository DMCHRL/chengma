package com.chengma.devplatform.service.dto;


import java.io.Serializable;

/**
 * A DTO for the SysRoleForm entity.
 */
public class SysRoleFormDTO implements Serializable {

    private String id;

    private String sysRoleId;

    private String sysFormId;

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

    public String getSysFormId() {
        return sysFormId;
    }

    public void setSysFormId(String sysFormId) {
        this.sysFormId = sysFormId;
    }
}

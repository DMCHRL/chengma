package com.suitong.devplatform.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SysRoleForm entity.
 */
public class SysRoleFormDTO implements Serializable {

    private Long id;

    private Long sysRoleId;

    private Long sysFormId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(Long sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public Long getSysFormId() {
        return sysFormId;
    }

    public void setSysFormId(Long sysFormId) {
        this.sysFormId = sysFormId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysRoleFormDTO sysRoleFormDTO = (SysRoleFormDTO) o;
        if(sysRoleFormDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysRoleFormDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysRoleFormDTO{" +
            "id=" + getId() +
            "}";
    }
}

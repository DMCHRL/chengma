package com.suitong.devplatform.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SysRoleComponent entity.
 */
public class SysRoleComponentDTO implements Serializable {

    private Long id;

    private Long sysRoleId;

    private Long sysComponentId;

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

    public Long getSysComponentId() {
        return sysComponentId;
    }

    public void setSysComponentId(Long sysComponentId) {
        this.sysComponentId = sysComponentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysRoleComponentDTO sysRoleComponentDTO = (SysRoleComponentDTO) o;
        if(sysRoleComponentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysRoleComponentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysRoleComponentDTO{" +
            "id=" + getId() +
            "}";
    }
}

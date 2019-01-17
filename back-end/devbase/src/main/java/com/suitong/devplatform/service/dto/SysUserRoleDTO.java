package com.suitong.devplatform.service.dto;


import com.suitong.devplatform.domain.SysUserRole;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SysUserRole entity.
 */
public class SysUserRoleDTO implements Serializable {

    private Long id;

    private Long sysRolesId;

    private Long sysUsersId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSysRolesId() {
        return sysRolesId;
    }

    public void setSysRolesId(Long sysRolesId) {
        this.sysRolesId = sysRolesId;
    }

    public Long getSysUsersId() {
        return sysUsersId;
    }

    public void setSysUsersId(Long sysUsersId) {
        this.sysUsersId = sysUsersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysUserRoleDTO sysUserRoleDTO = (SysUserRoleDTO) o;
        if(sysUserRoleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysUserRoleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysUserRoleDTO{" +
            "id=" + getId() +
            "}";
    }
}

package com.suitong.devplatform.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SysRoleMenu entity.
 */
public class SysRoleMenuDTO implements Serializable {

    private Long id;

    private Long sysRoleId;

    private Long sysMenuId;

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

    public Long getSysMenuId() {
        return sysMenuId;
    }

    public void setSysMenuId(Long sysMenuId) {
        this.sysMenuId = sysMenuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysRoleMenuDTO sysRoleMenuDTO = (SysRoleMenuDTO) o;
        if(sysRoleMenuDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysRoleMenuDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysRoleMenuDTO{" +
            "id=" + getId() +
            "}";
    }
}

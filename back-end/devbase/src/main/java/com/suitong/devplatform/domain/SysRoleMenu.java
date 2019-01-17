package com.suitong.devplatform.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SysRoleMenu.
 */
@Entity
@Table(name = "t_sys_role_menu")
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private SysRole sysRole;

    @OneToOne
    @JoinColumn(unique = true)
    private SysMenu sysMenu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public SysRoleMenu sysRole(SysRole sysRole) {
        this.sysRole = sysRole;
        return this;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public SysMenu getSysMenu() {
        return sysMenu;
    }

    public SysRoleMenu sysMenu(SysMenu sysMenu) {
        this.sysMenu = sysMenu;
        return this;
    }

    public void setSysMenu(SysMenu sysMenu) {
        this.sysMenu = sysMenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysRoleMenu sysRoleMenu = (SysRoleMenu) o;
        if (sysRoleMenu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysRoleMenu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysRoleMenu{" +
            "id=" + getId() +
            "}";
    }
}

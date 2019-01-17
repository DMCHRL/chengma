package com.suitong.devplatform.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SysRoleForm.
 */
@Entity
@Table(name = "t_sys_role_form")
public class SysRoleForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private SysRole sysRole;

    @OneToOne
    @JoinColumn(unique = true)
    private SysForm sysForm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public SysRoleForm sysRole(SysRole sysRole) {
        this.sysRole = sysRole;
        return this;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public SysForm getSysForm() {
        return sysForm;
    }

    public SysRoleForm sysForm(SysForm sysForm) {
        this.sysForm = sysForm;
        return this;
    }

    public void setSysForm(SysForm sysForm) {
        this.sysForm = sysForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysRoleForm sysRoleForm = (SysRoleForm) o;
        if (sysRoleForm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysRoleForm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysRoleForm{" +
            "id=" + getId() +
            "}";
    }
}

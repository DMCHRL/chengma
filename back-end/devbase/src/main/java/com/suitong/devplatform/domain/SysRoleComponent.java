package com.suitong.devplatform.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SysRoleComponent.
 */
@Entity
@Table(name = "t_sys_role_component")
public class SysRoleComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private SysRole sysRole;

    @OneToOne
    @JoinColumn(unique = true)
    private SysComponent sysComponent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public SysRoleComponent sysRole(SysRole sysRole) {
        this.sysRole = sysRole;
        return this;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public SysComponent getSysComponent() {
        return sysComponent;
    }

    public SysRoleComponent sysComponent(SysComponent sysComponent) {
        this.sysComponent = sysComponent;
        return this;
    }

    public void setSysComponent(SysComponent sysComponent) {
        this.sysComponent = sysComponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysRoleComponent sysRoleComponent = (SysRoleComponent) o;
        if (sysRoleComponent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysRoleComponent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysRoleComponent{" +
            "id=" + getId() +
            "}";
    }
}

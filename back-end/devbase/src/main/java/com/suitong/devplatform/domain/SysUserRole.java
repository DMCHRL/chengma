package com.suitong.devplatform.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SysUserRole.
 */
@Entity
@Table(name = "t_sys_user_role")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "sys_roles_id",unique = true)
    private SysRole sysRole;

    /*@OneToOne
    @JoinColumn(name = "sys_users_id", unique = true)
    private SysUser sysUser;*/

    @OneToOne
    @JoinColumn(name = "sys_users_id", unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysUserRole sysUserRole = (SysUserRole) o;
        if (sysUserRole.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysUserRole.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysUserRole{" +
            "id=" + getId() +
            "}";
    }
}

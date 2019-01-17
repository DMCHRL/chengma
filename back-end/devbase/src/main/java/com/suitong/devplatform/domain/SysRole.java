package com.suitong.devplatform.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A SysRole.
 */
@Entity
@Table(name = "t_sys_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_role_no")
    private String roleNo;

    @Column(name = "c_role_name")
    private String roleName;

    @Column(name = "c_description")
    private String description;

    @Column(name = "c_del_flag")
    private String delFlag;

    @Column(name = "i_create_by")
    private Long createBy;

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "i_last_update_by")
    private Long lastUpdateBy;

    @Column(name = "d_last_update_at")
    private Date lastUpdateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleNo() {
        return roleNo;
    }

    public SysRole roleNo(String roleNo) {
        this.roleNo = roleNo;
        return this;
    }

    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
    }

    public String getRoleName() {
        return roleName;
    }

    public SysRole roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public SysRole createBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public SysRole createAt(Date createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }

    public SysRole lastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateAt() {
        return lastUpdateAt;
    }

    public SysRole lastUpdateAt(Date lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
        return this;
    }

    public void setLastUpdateAt(Date lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysRole sysRole = (SysRole) o;
        if (sysRole.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysRole.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysRole{" +
            "id=" + getId() +
            ", roleNo='" + getRoleNo() + "'" +
            ", roleName='" + getRoleName() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", lastUpdateBy='" + getLastUpdateBy() + "'" +
            ", lastUpdateAt='" + getLastUpdateAt() + "'" +
            "}";
    }
}

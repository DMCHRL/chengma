package com.suitong.devplatform.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A SysUser.
 */
@Entity
@Table(name = "t_sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_account")
    private String account;

    @Column(name = "c_username")
    private String username;

    @Column(name = "c_password")
    private String password;

    @Column(name = "c_card_no")
    private String cardNo;

    @Column(name = "c_sex")
    private String sex;

    @Column(name = "c_id_no")
    private String idNo;

    @Column(name = "c_org_id")
    private String orgId;

    @Column(name = "c_telephone")
    private String telephone;

    @Column(name = "c_mobile")
    private String mobile;

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

    public String getAccount() {
        return account;
    }

    public SysUser account(String account) {
        this.account = account;
        return this;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public SysUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public SysUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNo() {
        return cardNo;
    }

    public SysUser cardNo(String cardNo) {
        this.cardNo = cardNo;
        return this;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSex() {
        return sex;
    }

    public SysUser sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdNo() {
        return idNo;
    }

    public SysUser idNo(String idNo) {
        this.idNo = idNo;
        return this;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getOrgId() {
        return orgId;
    }

    public SysUser orgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getTelephone() {
        return telephone;
    }

    public SysUser telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public SysUser mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public SysUser delFlag(String delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public SysUser createBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public SysUser createAt(Date createAt) {
        this.createAt = createAt;
        return this;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }

    public SysUser lastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
        return this;
    }

    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateAt() {
        return lastUpdateAt;
    }

    public SysUser lastUpdateAt(Date lastUpdateAt) {
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
        SysUser sysUser = (SysUser) o;
        if (sysUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysUser{" +
            "id=" + getId() +
            ", account='" + getAccount() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", cardNo='" + getCardNo() + "'" +
            ", sex='" + getSex() + "'" +
            ", idNo='" + getIdNo() + "'" +
            ", orgId='" + getOrgId() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", delFlag='" + getDelFlag() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", lastUpdateBy='" + getLastUpdateBy() + "'" +
            ", lastUpdateAt='" + getLastUpdateAt() + "'" +
            "}";
    }
}

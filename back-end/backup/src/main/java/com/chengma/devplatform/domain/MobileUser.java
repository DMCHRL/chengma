package com.chengma.devplatform.domain;

import com.chengma.devplatform.common.entity.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * A MobileValidate.
 */
@Entity
@Table(name = "t_lhfz_mobile_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MobileUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_name")
    private String name;

    @Column(name = "c_head_img")
    private String headImg;

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "c_mobile")
    private String mobile;

    @Column(name = "d_update_at")
    private Date updateAt;

    @Column(name = "c_status")
    private String status;        //标记是否是有新消息

    @Column(name = "c_id_positive")
    private String idPositive;        //身份证正面

    @Column(name = "c_id_negative")
    private String idNegative;        //身份证反面

    @Column(name = "c_cid")
    private String cid;        //个推识别Id

    @Column(name = "c_email")
    private String email;        //邮箱

    @Column(name = "c_password")
    private String password;        //支付密码

    @Column(name = "c_user_id")
    private String userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdPositive() {
        return idPositive;
    }

    public void setIdPositive(String idPositive) {
        this.idPositive = idPositive;
    }

    public String getIdNegative() {
        return idNegative;
    }

    public void setIdNegative(String idNegative) {
        this.idNegative = idNegative;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

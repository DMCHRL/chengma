package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * A SysRole.
 */
@Entity
@Table(name = "t_mt4_configure")
public class Mt4Configure extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_server")
    private String server;

    @Column(name = "c_uid")
    private Integer uid;

    @Column(name = "c_password")
    private String password;

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "d_update_at")
    private Date updateAt;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}

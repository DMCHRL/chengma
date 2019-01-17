package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A SysRole.
 */
@Entity
@Table(name = "t_tlb_account_control")
public class TlbAccountControl extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_account")
    private String account;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "c_trade")
    private String trade;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }
}

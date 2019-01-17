package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A SysRole.
 */
@Entity
@Table(name = "t_tlb_user_feedback")
public class TlbUserFeedback extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_username")
    private String userName;

    @Column(name = "c_tlb_account")
    private String tlbAccount;

    @Column(name = "c_phone")
    private String phone;

    @Column(name = "c_feedback_type")
    private String feedbackType;

    @Column(name = "c_feedback_context")
    private String feedbackContext;

    @Column(name = "c_create_at")
    private Date createAt;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedbackContext() {
        return feedbackContext;
    }

    public void setFeedbackContext(String feedbackContext) {
        this.feedbackContext = feedbackContext;
    }

    public String getTlbAccount() {
        return tlbAccount;
    }

    public void setTlbAccount(String tlbAccount) {
        this.tlbAccount = tlbAccount;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "TlbUserFeedback{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", tlbAccount='" + tlbAccount + '\'' +
                ", phone='" + phone + '\'' +
                ", feedbackType='" + feedbackType + '\'' +
                ", feedbackContext='" + feedbackContext + '\'' +
                ", createAt='" + createAt + '\'' +
                '}';
    }
}

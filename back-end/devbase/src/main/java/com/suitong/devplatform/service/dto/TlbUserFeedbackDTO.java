package com.suitong.devplatform.service.dto;


import javax.persistence.*;
import java.io.Serializable;

/**
 * A SysRole.
 */
public class TlbUserFeedbackDTO {

    private Long id;

    private String userName;

    private String tlbAccount;

    private String phone;

    private String feedbackType;

    private String feedbackContext;

    public String getTlbAccount() {
        return tlbAccount;
    }

    public void setTlbAccount(String tlbAccount) {
        this.tlbAccount = tlbAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}

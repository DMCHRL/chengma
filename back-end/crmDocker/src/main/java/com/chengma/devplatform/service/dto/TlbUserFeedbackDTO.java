package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * A SysRole.
 */
public class TlbUserFeedbackDTO {

    private String id;

    private String userName;

    private String tlbAccount;

    private String phone;

    private String feedbackType;

    private String feedbackContext;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    public String getTlbAccount() {
        return tlbAccount;
    }

    public void setTlbAccount(String tlbAccount) {
        this.tlbAccount = tlbAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}

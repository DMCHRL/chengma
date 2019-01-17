package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import java.util.Date;

/**
 * A SysRole.
 */
public class HppUserDTO {

    private String id;

    private String username;

    private String phone;

    private String email;

    private String address;

    private String recommendation;

    public String getRecommendationName() {
        return recommendationName;
    }

    public void setRecommendationName(String recommendationName) {
        this.recommendationName = recommendationName;
    }

    private String recommendationName;

    private String idType;

    private String idNumber;

    private String idPositive;

    private String idOther;

    private String cardNumber;

    private String openingBank;

    private String branch;

    private String cardPositive;

    private String cardOther;

    private Integer state;

    private String spare1;

    private String spare2;

    private String status;

    private String type;

    private String approvedBy;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date approvedAt;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    private String userId;

    private String approvedname;

    private String sinksType;     //汇商类别

    private String sinksName;     //汇商名

    private String userType;      //用户类型

    private String secondType;     //二次身份证明类型 driver charge passport

    private String secondPositive;  //二次身份证明图片

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdPositive() {
        return idPositive;
    }

    public void setIdPositive(String idPositive) {
        this.idPositive = idPositive;
    }

    public String getIdOther() {
        return idOther;
    }

    public void setIdOther(String idOther) {
        this.idOther = idOther;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCardPositive() {
        return cardPositive;
    }

    public void setCardPositive(String cardPositive) {
        this.cardPositive = cardPositive;
    }

    public String getCardOther() {
        return cardOther;
    }

    public void setCardOther(String cardOther) {
        this.cardOther = cardOther;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSpare1() {
        return spare1;
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1;
    }

    public String getSpare2() {
        return spare2;
    }

    public void setSpare2(String spare2) {
        this.spare2 = spare2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(Date approvedAt) {
        this.approvedAt = approvedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getApprovedname() {
        return approvedname;
    }

    public void setApprovedname(String approvedname) {
        this.approvedname = approvedname;
    }

    public String getSinksType() {
        return sinksType;
    }

    public void setSinksType(String sinksType) {
        this.sinksType = sinksType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSecondType() {
        return secondType;
    }

    public void setSecondType(String secondType) {
        this.secondType = secondType;
    }

    public String getSecondPositive() {
        return secondPositive;
    }

    public void setSecondPositive(String secondPositive) {
        this.secondPositive = secondPositive;
    }

    public String getSinksName() {
        return sinksName;
    }

    public void setSinksName(String sinksName) {
        this.sinksName = sinksName;
    }
}

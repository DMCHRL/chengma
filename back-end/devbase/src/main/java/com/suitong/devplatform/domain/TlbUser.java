package com.suitong.devplatform.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A SysRole.
 */
@Entity
@Table(name = "t_tlb_user")
public class TlbUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_username")
    private String username;

    @Column(name = "c_phone")
    private String phone;

    @Column(name = "c_email")
    private String email;

    @Column(name = "c_address")
    private String address;

    @Column(name = "c_recommendation")
    private String recommendation;

    @Column(name = "c_idType")
    private String idType;

    @Column(name = "c_id_number")
    private String idNumber;

    @Column(name = "c_id_positive")
    private String idPositive;

    @Column(name = "c_id_other")
    private String idOther;

    @Column(name = "c_card_number")
    private String cardNumber;

    @Column(name = "c_opening_bank")
    private String openingBank;

    @Column(name = "c_branch")
    private String branch;


    @Column(name = "c_card_positive")
    private String cardPositive;

    @Column(name = "c_card_other")
    private String cardOther;

    @Column(name = "i_state")
    private Integer state;

    @Column(name = "c_spare1")
    private String spare1;

    @Column(name = "c_spare2")
    private String spare2;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "TlbUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", recommendation='" + recommendation + '\'' +
                ", idType='" + idType + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", idPositive='" + idPositive + '\'' +
                ", idOther='" + idOther + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", openingBank='" + openingBank + '\'' +
                ", branch='" + branch + '\'' +
                ", cardPositive='" + cardPositive + '\'' +
                ", cardOther='" + cardOther + '\'' +
                ", state=" + state +
                ", spare1='" + spare1 + '\'' +
                ", spare2='" + spare2 + '\'' +
                '}';
    }
}

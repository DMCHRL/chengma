package com.chengma.devplatform.service.dto;

/**
 * Created by Administrator on 2018/8/9.
 */

import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import java.util.Date;

/**
 * 报名培训实体
 */
public class HppExamApplyDTO {


    private String id;

    private String phone;     //报名手机号

    private String email;     //邮箱

    private String username;     //报名姓名

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createAt;     //报名时间

    private String status;     //报名状态 APPLYING PASSED REJECT 默认PASSED

    private String examName;  //培训名称

    private String orderNum;     //订单号

    private String courseMealId;     //套餐Id

    private String body;     //订单body 此为 course

    private String mealName;

    private String mealInclude;

    private Double mealPrice;

    private String payType;     //付款方式

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCourseMealId() {
        return courseMealId;
    }

    public void setCourseMealId(String courseMealId) {
        this.courseMealId = courseMealId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealInclude() {
        return mealInclude;
    }

    public void setMealInclude(String mealInclude) {
        this.mealInclude = mealInclude;
    }

    public Double getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(Double mealPrice) {
        this.mealPrice = mealPrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}

package com.chengma.devplatform.domain;

/**
 * Created by Administrator on 2018/8/9.
 */

import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 报名培训实体
 */
@Entity
@Table(name = "t_hpp_exam_apply")
public class HppExamApply extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_exam_name")
    private String examName;     //考证名称

    @Column(name = "c_phone")
    private String phone;     //报名手机号

    @Column(name = "c_email")
    private String email;     //邮箱

    @Column(name = "c_username")
    private String username;     //报名姓名

    @Column(name = "d_create_at")
    private Date createAt;     //报名时间

    @Column(name = "c_status")
    private String status;     //报名状态 APPLYING PASSED REJECT 默认PASSED

    @Column(name = "c_order_num")
    private String orderNum;     //订单号

    @Column(name = "c_course_meal_id")
    private String courseMealId;     //套餐Id

    @Column(name = "c_pay_type")
    private String payType;     //付款方式

    @Column(name = "c_meal_name")
    private String mealName;     //套餐名字

    @Column(name = "c_meal_include")
    private String mealInclude;     //套餐包含

    @Column(name = "c_meal_price")
    private Double mealPrice;

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCourseMealId() {
        return courseMealId;
    }

    public void setCourseMealId(String courseMealId) {
        this.courseMealId = courseMealId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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
}

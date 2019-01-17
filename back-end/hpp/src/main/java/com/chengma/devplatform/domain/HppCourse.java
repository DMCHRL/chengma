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
 * 线下培训实体
 */
@Entity
@Table(name = "t_hpp_course")
public class HppCourse extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_train_name")
    private String trainName;     //培训名称

    @Column(name = "c_img")
    private String img;     //配图

    @Column(name = "c_train_time")
    private String trainTime;     //培训时长

    @Column(name = "c_type")
    private String type;    //课程类型

    @Column(name = "c_teacher")
    private String teacher;    //主讲老师

    @Column(name = "c_hot_tag")
    private String hotTag;    //热门标签

    @Column(name = "c_introduction")
    private String introduction;    //课程介绍

    @Column(name = "c_context")
    private String context;    //详情

    @Column(name = "d_create_time")
    private Date createTime;

    @Column(name = "d_update_time")
    private Date updateTime;

    @Column(name = "c_create_by")
    private String createBy;    //

    @Column(name = "i_exchange_num")
    private Integer exchangeNum;     //兑换次数

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(String trainTime) {
        this.trainTime = trainTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getHotTag() {
        return hotTag;
    }

    public void setHotTag(String hotTag) {
        this.hotTag = hotTag;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Integer getExchangeNum() {
        return exchangeNum;
    }

    public void setExchangeNum(Integer exchangeNum) {
        this.exchangeNum = exchangeNum;
    }
}

package com.chengma.devplatform.service.dto;

import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/25.
 */
public class HppExamDTO {

    private String id;

    private String trainName;     //培训名称

    private String img;     //配图

    private String trainTime;     //培训时长

    private String type;    //课程类型

    private String teacher;    //主讲老师

    private String hotTag;    //热门标签

    private String introduction;    //课程介绍

    private String context;    //详情

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createTime;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date updateTime;

    private List<HppCourseMealDTO> mealList ; //套餐

    private String createBy;

    private String address;    //考证地点

    private Integer exchangeNum;     //兑换次数

    private Integer total; //总报名人数

    private Integer monthTotal; //近30天报名人数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<HppCourseMealDTO> getMealList() {
        return mealList;
    }

    public void setMealList(List<HppCourseMealDTO> mealList) {
        this.mealList = mealList;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getExchangeNum() {
        return exchangeNum;
    }

    public void setExchangeNum(Integer exchangeNum) {
        this.exchangeNum = exchangeNum;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getMonthTotal() {
        return monthTotal;
    }

    public void setMonthTotal(Integer monthTotal) {
        this.monthTotal = monthTotal;
    }
}

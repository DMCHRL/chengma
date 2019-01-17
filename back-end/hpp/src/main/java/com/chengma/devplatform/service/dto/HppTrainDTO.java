package com.chengma.devplatform.service.dto;

/**
 * Created by Administrator on 2018/8/9.
 */

import com.chengma.devplatform.CustomDateSerializer;
import com.chengma.devplatform.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 线下培训实体
 */
public class HppTrainDTO  {


    private String id;

    private String trainName;     //培训名称

    private String img1;     //配图1

    private String img2;     //配图2

    private String listImg;     //列表显示图

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date trainTime;     //培训时间

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date endTime;     //培训结束时间

    private String trainPlace;     //地点

    private String sponsor;    //主办方

    private String unionSponsor;    //联合主办方

    private String organizer;    //承办方

    private String theNaming;    //总冠名

    private String coOperation;    //协办方

    private String supportMedia;    //首席支持媒体

    private String trainText;    //培训描述

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createAt;    //创建时间

    private String userId;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date updateAt;

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getListImg() {
        return listImg;
    }

    public void setListImg(String listImg) {
        this.listImg = listImg;
    }

    public Date getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(Date trainTime) {
        this.trainTime = trainTime;
    }

    public String getTrainPlace() {
        return trainPlace;
    }

    public void setTrainPlace(String trainPlace) {
        this.trainPlace = trainPlace;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getUnionSponsor() {
        return unionSponsor;
    }

    public void setUnionSponsor(String unionSponsor) {
        this.unionSponsor = unionSponsor;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getTheNaming() {
        return theNaming;
    }

    public void setTheNaming(String theNaming) {
        this.theNaming = theNaming;
    }

    public String getCoOperation() {
        return coOperation;
    }

    public void setCoOperation(String coOperation) {
        this.coOperation = coOperation;
    }

    public String getSupportMedia() {
        return supportMedia;
    }

    public void setSupportMedia(String supportMedia) {
        this.supportMedia = supportMedia;
    }

    public String getTrainText() {
        return trainText;
    }

    public void setTrainText(String trainText) {
        this.trainText = trainText;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}

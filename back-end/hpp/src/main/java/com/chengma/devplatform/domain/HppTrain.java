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
 * 线下培训实体,废弃了
 */
@Entity
@Table(name = "t_hpp_train")
public class HppTrain extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_train_name")
    private String trainName;     //培训名称

    @Column(name = "c_img1")
    private String img1;     //配图1

    @Column(name = "c_img2")
    private String img2;     //配图2

    @Column(name = "c_list_img")
    private String listImg;     //列表显示图

    @Column(name = "d_train_time")
    private Date trainTime;     //培训时间

    @Column(name = "d_end_time")
    private Date endTime;     //培训结束时间

    @Column(name = "c_train_place")
    private String trainPlace;     //地点

    @Column(name = "c_sponsor")
    private String sponsor;    //主办方

    @Column(name = "c_union_sponsor")
    private String unionSponsor;    //联合主办方

    @Column(name = "c_organizer")
    private String organizer;    //承办方

    @Column(name = "c_the_naming")
    private String theNaming;    //总冠名

    @Column(name = "c_co_operation")
    private String coOperation;    //协办方

    @Column(name = "c_support_media")
    private String supportMedia;    //首席支持媒体

    @Column(name = "c_train_text")
    private String trainText;    //培训描述

    @Column(name = "d_create_at")
    private Date createAt;    //创建时间

    @Column(name = "c_user_id")
    private String userId;

    @Column(name = "d_update_at")
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

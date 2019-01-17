package com.chengma.devplatform.service.dto;

/**
 * Created by Administrator on 2018/8/9.
 */

import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * 线下培训实体
 */
public class HppVideoDTO {


    private String id;

    private String videoTypeId;     //视频类别

    private String videoName;     //视频名称

    private String videoUrl;     //视频url

    private String videoText;     //视频描述

    private Integer playNum;     //播放次数

    private Integer feeFlag;     //是否收费 1,收费 2,免费

    private Double price;     //现金价格

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createAt;    //创建时间

    private Integer exchangeNum;     //兑换次数

    private Double integralPrice;     //积分价格

    private Integer phase;           //期数

    private String img;          //图片

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date updateAt;

    private Double exchangePrice; //最近一次兑换价格

    private String videoTypeName;  //视频类别名称

    private Integer sortNum;     //排序号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoTypeId() {
        return videoTypeId;
    }

    public void setVideoTypeId(String videoTypeId) {
        this.videoTypeId = videoTypeId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoText() {
        return videoText;
    }

    public void setVideoText(String videoText) {
        this.videoText = videoText;
    }

    public Integer getPlayNum() {
        return playNum;
    }

    public void setPlayNum(Integer playNum) {
        this.playNum = playNum;
    }

    public Integer getFeeFlag() {
        return feeFlag;
    }

    public void setFeeFlag(Integer feeFlag) {
        this.feeFlag = feeFlag;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getExchangeNum() {
        return exchangeNum;
    }

    public void setExchangeNum(Integer exchangeNum) {
        this.exchangeNum = exchangeNum;
    }

    public Double getIntegralPrice() {
        return integralPrice;
    }

    public void setIntegralPrice(Double integralPrice) {
        this.integralPrice = integralPrice;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Double getExchangePrice() {
        return exchangePrice;
    }

    public void setExchangePrice(Double exchangePrice) {
        this.exchangePrice = exchangePrice;
    }

    public String getVideoTypeName() {
        return videoTypeName;
    }

    public void setVideoTypeName(String videoTypeName) {
        this.videoTypeName = videoTypeName;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
}

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
@Table(name = "t_hpp_video")
public class HppVideo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_video_type_id")
    private String videoTypeId;     //视频类别

    @Column(name = "c_video_name")
    private String videoName;     //视频名称

    @Column(name = "c_video_url")
    private String videoUrl;     //视频url

    @Column(name = "c_video_text")
    private String videoText;     //视频描述

    @Column(name = "i_play_num")
    private Integer playNum;     //播放次数

    @Column(name = "i_fee_flag")
    private Integer feeFlag;     //是否收费 1,收费 2,免费

    @Column(name = "i_price")
    private Double price;     //现金价格

    @Column(name = "d_create_at")
    private Date createAt;    //创建时间

    @Column(name = "i_exchange_num")
    private Integer exchangeNum;     //兑换次数

    @Column(name = "i_integral_price")
    private Double integralPrice;     //积分价格

    @Column(name = "i_phase")
    private Integer phase;          //期数

    @Column(name = "d_update_at")
    private Date updateAt;       //最近一次更新时间

    @Column(name = "c_img")
    private String img;          //图片

    @Column(name = "i_sort_num")
    private Integer sortNum;     //排序号

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

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
}

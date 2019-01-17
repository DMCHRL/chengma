package com.chengma.devplatform.domain;

import com.chengma.devplatform.common.entity.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * A MobileValidate.
 */
@Entity
@Table(name = "t_hpp_mobile_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HppMobileUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_user_name")
    private String userName;

    @Column(name = "c_head_img")
    private String headImg;

    @Column(name = "d_create_at")
    private Date createAt;

    @Column(name = "c_phone")
    private String phone;

    @Column(name = "d_update_at")
    private Date updateAt;

    @Column(name = "c_flag")
    private String flag;        //标记是否是有新消息

    @Column(name = "c_recommendation")
    private String recommendation;        //推荐码

    @Column(name = "c_recommendation_ed")
    private String recommendationEd;        //推荐人 (码)

    @Column(name = "c_cid")
    private String cid;        //个推识别Id

    @Column(name = "c_follow_flag")
    private String followFlag;        //跟单用户标记

    @Column(name = "c_buy_flag")
    private String buyFlag;        //消费用户标记

    @Column(name = "c_open_flag")
    private String openFlag;        //消费用户标记

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getFollowFlag() {
        return followFlag;
    }

    public void setFollowFlag(String followFlag) {
        this.followFlag = followFlag;
    }

    public String getBuyFlag() {
        return buyFlag;
    }

    public void setBuyFlag(String buyFlag) {
        this.buyFlag = buyFlag;
    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    public String getRecommendationEd() {
        return recommendationEd;
    }

    public void setRecommendationEd(String recommendationEd) {
        this.recommendationEd = recommendationEd;
    }
}

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
@Table(name = "t_hpp_video_type")
public class HppVideoType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_video_type_name")
    private String videoTypeName;     //视频类别名称

    @Column(name = "c_text")
    private String text;     //备注

    @Column(name = "d_create_at")
    private Date createAt;    //创建时间

    @Column(name = "c_user_id")
    private String userId;

    @Column(name = "c_img")
    private String img;        //列表显示图片

    @Column(name = "d_update_at")
    private Date updateAt;

    @Column(name = "c_day_push")
    private String dayPush;     //每日推荐 YES or NO

    @Column(name = "c_approve_id")
    private String approveId;     //处理人Id

    @Column(name = "i_sort_num")
    private Integer sortNum;     //排序号

    public String getVideoTypeName() {
        return videoTypeName;
    }

    public void setVideoTypeName(String videoTypeName) {
        this.videoTypeName = videoTypeName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getDayPush() {
        return dayPush;
    }

    public void setDayPush(String dayPush) {
        this.dayPush = dayPush;
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
}

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
 * 广告实体
 */
@Entity
@Table(name = "t_hpp_advertisement")
public class HppAdvertisement extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_img")
    private String img;     //图片

    @Column(name = "d_create_at")
    private Date createAt;    //创建时间

    @Column(name = "d_update_at")
    private Date updateAt;

    @Column(name = "c_approve_id")
    private String approveId;    //设置人Id

    @Column(name = "c_url")
    private String url;

    @Column(name = "c_type")
    private String type;     //类型 HOME OR VIDEO

    @Column(name = "c_text")
    private String text;     //备注

    @Column(name = "i_sort_num")
    private Integer sortNum;     //排序号

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
}

package com.chengma.devplatform.domain;

import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/9.
 */
@Entity
@Table(name="t_notice_sign")
public class NoticeSign extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="c_mobile")
    private String mobile;  //电话号码

    @Column(name="c_notice_id")
    private String noticeId; //消息体Id

    @Column(name="c_status")
    private String status; //是否阅读

    @Column(name="d_read_time")
    private Date readTime; //阅读时间

    @Column(name="c_del_flag")
    private String delFlag;  //删除标记

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}

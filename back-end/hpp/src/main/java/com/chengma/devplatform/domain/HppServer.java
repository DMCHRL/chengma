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
 * 客服信息
 */
@Entity
@Table(name = "t_hpp_server")
public class HppServer extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_type")
    private String type;

    @Column(name = "c_text")
    private String text;

    @Column(name = "c_label")
    private String label;

    @Column(name = "d_update_time")
    private Date updateTime;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

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
 * app导航栏
 */
@Entity
@Table(name = "t_hpp_navigation")
public class HppNavigation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_name")
    private String name;     //名称

    @Column(name = "c_img")
    private String img;     //配图

    @Column(name = "i_day")
    private Integer day;     //单日点击量

    @Column(name = "i_num")
    private Integer num;     //点击量

    @Column(name = "c_flag")
    private String flag;    //标记

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}

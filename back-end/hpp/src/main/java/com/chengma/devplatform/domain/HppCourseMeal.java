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
@Table(name = "t_hpp_course_meal")
public class HppCourseMeal extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_name")
    private String name;     //套餐名称

    @Column(name = "i_price")
    private Double price;     //套餐价格

    @Column(name = "c_include")
    private String include;     //套餐包含

    @Column(name = "d_create_time")
    private Date createTime;

    @Column(name = "d_update_time")
    private Date updateTime;

    @Column(name = "i_sort")
    private Integer sort; //排序

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
